package com.jepprot.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jepprot.submission2.repository.DataUser
import com.jepprot.submission2.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    val mFollowing = MutableLiveData<ArrayList<DataUser>>()

    fun setFollowing(username: String) {
        ApiClient.apiInstance
            .followingTab(username)
            .enqueue(object : Callback<ArrayList<DataUser>> {
                override fun onResponse(
                    call: Call<ArrayList<DataUser>>,
                    response: Response<ArrayList<DataUser>>
                ) {
                    if (response.isSuccessful) {
                        mFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataUser>>, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                }

            })
    }

    fun getFollowingTab(): LiveData<ArrayList<DataUser>> {
        return mFollowing
    }
}