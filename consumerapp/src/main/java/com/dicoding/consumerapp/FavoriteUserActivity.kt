package com.dicoding.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.consumerapp.databinding.ActivitySqlDataBinding
import com.dicoding.consumerapp.db.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.dicoding.githubuser.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: FavUserAdapter
    private lateinit var bindin: ActivitySqlDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindin = ActivitySqlDataBinding.inflate(layoutInflater)
        setContentView(bindin.root)

        adapter = FavUserAdapter()
        adapter.notifyDataSetChanged()

        loadFavUserAsync()

        bindin.apply {
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
            }
        }
    }
}