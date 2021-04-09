package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.api.RetrofitClient
import com.dicoding.githubuser.model.UserSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val userFollowers = MutableLiveData<ArrayList<UserSearch>>()

    fun setFollowers(username: String){
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<UserSearch>> {
                override fun onResponse(
                    call: Call<ArrayList<UserSearch>>,
                    response: Response<ArrayList<UserSearch>>
                ) {
                    if (response.isSuccessful){
                        userFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserSearch>>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun getFollowers(): LiveData<ArrayList<UserSearch>>{
        return userFollowers
    }
}