package com.dicoding.githubuser.api

import com.dicoding.githubuser.model.UserDetailResponse
import com.dicoding.githubuser.model.UserResponse
import com.dicoding.githubuser.model.UserSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username:String  
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserSearch>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserSearch>>

}