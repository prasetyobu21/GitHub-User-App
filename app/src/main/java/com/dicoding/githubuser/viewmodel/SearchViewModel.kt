package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.api.RetrofitClient
import com.dicoding.githubuser.model.UserResponse
import com.dicoding.githubuser.model.UserSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    val userSearchList = MutableLiveData<ArrayList<UserSearch>>()

    fun setSearchParameter(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        userSearchList.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getSearchResult(): LiveData<ArrayList<UserSearch>> {
        return userSearchList
    }
}