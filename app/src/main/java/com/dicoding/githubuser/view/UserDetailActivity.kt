package com.dicoding.githubuser.view

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.githubuser.db.DatabaseContract
import com.dicoding.githubuser.db.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.dicoding.githubuser.db.FavoriteHelper
import com.dicoding.githubuser.helper.MappingHelper
import com.dicoding.githubuser.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val data = favoriteHelper.queryById(1.toString())

        Log.d("dat", data.toString())


        val username = intent.getStringExtra(EXTRA_NAME).toString()
        val bundle = Bundle()

        bundle.putString(FollowersFollowingActivity.EXTRA_NAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserDetailViewModel::class.java)

        showLoading(true)
        viewModel.setUserDetail(username)

        viewModel.getUserDetail().observe(this, {
            showLoading(false)
            binding.apply {
                if (it.name == null) tvUsername.text = it.login
                else tvUsername.text = it.name
                tvFollowers.text = it.followers.toString() + " Followers"
                tvFollowing.text = it.following.toString() + " Following"
                tvUserOrgs.text = it.company
                if (it.bio == null) tvBio.text = "No bio"
                else tvBio.text = it.bio

                val url = it.html_url
                val login = it.login
                var name = it.name
                var avatar = it.avatar_url

                if (name == null) name = login

                val cursor = favoriteHelper.queryById(login)
                val fav = MappingHelper.mapCursorToArrayList(cursor)

                if (fav.size > 0) {
                    btnFavorite.text = "Remove from favorite"
                    btnFavorite.setOnClickListener {
                        val uriLogin = Uri.parse(CONTENT_URI.toString() + "/" + login)
                        contentResolver.delete(uriLogin, null, null)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        runOnUiThread { btnFavorite.text = "Add to favorite" }
                    }
                } else {
                    btnFavorite.text = "Add to favorite"
                    btnFavorite.setOnClickListener {
                        val values = ContentValues()
                        values.put(DatabaseContract.FavoriteUserColumns.LOGIN, login)
                        values.put(DatabaseContract.FavoriteUserColumns.NAME, name)
                        values.put(DatabaseContract.FavoriteUserColumns.AVATAR_URL, avatar)

                        contentResolver.insert(CONTENT_URI, values)

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        runOnUiThread { btnFavorite.text = "Remove from favorite" }
                    }
                }

                tvFollowers.setOnClickListener {
                    val FollowIntent =
                        Intent(this@UserDetailActivity, FollowersFollowingActivity::class.java)
                    FollowIntent.putExtra(FollowersFollowingActivity.EXTRA_NAME, login)
                    startActivity(FollowIntent)
                }

                tvFollowing.setOnClickListener {
                    val FollowIntent =
                        Intent(this@UserDetailActivity, FollowersFollowingActivity::class.java)
                    FollowIntent.putExtra(FollowersFollowingActivity.EXTRA_NAME, login)
                    startActivity(FollowIntent)
                }

                Glide.with(this@UserDetailActivity)
                    .load(it.avatar_url)
                    .apply(RequestOptions().override(350, 350))
                    .into(imgUserPhoto)

                btnProfileWeb.setOnClickListener {
                    val openURL = Intent(android.content.Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    startActivity(openURL)
                }

                val sectionsPagerAdapter = FollowerSectionPagerAdapter(
                    this@UserDetailActivity,
                    supportFragmentManager,
                    bundle
                )
                viewPager.adapter = sectionsPagerAdapter
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}