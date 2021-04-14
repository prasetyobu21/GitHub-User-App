package com.dicoding.githubuser.helper

import android.database.Cursor
import com.dicoding.githubuser.db.DatabaseContract
import com.dicoding.githubuser.entity.FavoriteUser

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<FavoriteUser> {
        val FavoriteUserList = ArrayList<FavoriteUser>()
        val dbc = DatabaseContract.FavoriteUserColumns
        userCursor?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(dbc.NAME))
                val login = getString(getColumnIndexOrThrow(dbc.LOGIN))
                val avatar_url = getString(getColumnIndexOrThrow(dbc.AVATAR_URL))
                FavoriteUserList.add(FavoriteUser(name, login, avatar_url))
            }
        }
        return FavoriteUserList
    }

    fun mapCursorToObject(favoriteCursor: Cursor?): FavoriteUser{
        val dbc = DatabaseContract.FavoriteUserColumns
        var favorite = FavoriteUser()
        favoriteCursor?.apply {
            moveToFirst()
            val name = getString(getColumnIndexOrThrow(dbc.NAME))
            val login = getString(getColumnIndexOrThrow(dbc.LOGIN))
            val avatar_url = getString(getColumnIndexOrThrow(dbc.AVATAR_URL))
            favorite = FavoriteUser(name, login, avatar_url)
        }
        return favorite
    }
}