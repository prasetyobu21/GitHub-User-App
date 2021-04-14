package com.dicoding.githubuser.model

data class UserDetailResponse(
    val id: Int,
    val login: String,
    val name: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val followers: Int,
    val following: Int,
    val company: String,
    val html_url: String,
    val bio: String
)
