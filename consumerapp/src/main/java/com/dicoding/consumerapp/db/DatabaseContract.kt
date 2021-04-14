package com.dicoding.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

const val AUTHORITY = "com.dicoding.githubuser"
const val SCHEME = "content"

internal class DatabaseContract {

    internal class FavoriteUserColumns: BaseColumns{
        companion object{
            const val TABLE_NAME = "favorite_user"
            const val LOGIN = "login"
            const val NAME = "name"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}