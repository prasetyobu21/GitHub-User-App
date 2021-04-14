package com.dicoding.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.ActivitySqlDataBinding
import com.dicoding.githubuser.db.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.dicoding.githubuser.db.FavoriteHelper
import com.dicoding.githubuser.entity.FavoriteUser
import com.dicoding.githubuser.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: FavUserAdapter
    private lateinit var binding: ActivitySqlDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqlDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : FavUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FavoriteUser) {
                val UserDetailIntent = Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
                UserDetailIntent.putExtra(UserDetailActivity.EXTRA_NAME, data.login)
                startActivity(UserDetailIntent)
            }

        })

        loadFavUserAsync()

        binding.apply {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFav.setHasFixedSize(true)
            rvFav.adapter = adapter
        }
    }

    fun loadFavUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {

            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val fav = deferredFavorite.await()
            if (fav.size > 0) {
                adapter.listFavUser = fav
            } else {
                adapter.listFavUser = ArrayList()
            }
        }
    }
}