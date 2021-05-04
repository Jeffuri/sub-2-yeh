package com.jepprot.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jepprot.submission2.repository.DataUser
import com.jepprot.submission2.repository.DataDetail
import com.jepprot.submission2.repository.ResponseJson
import com.jepprot.submission2.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val mListUser = MutableLiveData<ArrayList<DataUser>>()
    private val userDetail = MutableLiveData<DataDetail>()
    
    fun setUser(query: String) {
        ApiClient.apiInstance
            .searchUser(query)
            .enqueue(object : Callback<ResponseJson> {
                override fun onResponse(
                    call: Call<ResponseJson>,
                    response: Response<ResponseJson>
                ) {
                    if (response.isSuccessful){
                        mListUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseJson>, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                }

            })
    }

    fun searchUser(): LiveData<ArrayList<DataUser>>{
        return mListUser
    }

    fun setDetailGithub(username: String) {
        ApiClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DataDetail>{
                override fun onResponse(
                    call: Call<DataDetail>,
                    response: Response<DataDetail>
                ) {
                    if (response.isSuccessful){
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DataDetail>, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                }
            })
    }

    fun getDetailGithub(): LiveData<DataDetail> {
        return userDetail
    }
}