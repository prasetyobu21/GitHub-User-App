package com.dicoding.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityFollowersFollowingBinding
import com.google.android.material.tabs.TabLayoutMediator

class FollowersFollowingActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var binding: ActivityFollowersFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowersFollowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME).toString()
        val bundle = Bundle()

        bundle.putString(EXTRA_NAME, username)

        val sectionsPagerAdapter = FollowerSectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){
            tab,position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
}