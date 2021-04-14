package com.dicoding.githubuser.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteUser(
    var name: String? = null,
    var login: String? = null,
    var avatar_url: String? = null
) : Parcelable
