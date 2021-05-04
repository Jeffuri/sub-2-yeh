package com.jepprot.submission2.retrofit

import com.jepprot.submission2.repository.DataUser
import com.jepprot.submission2.repository.DataDetail
import com.jepprot.submission2.repository.ResponseJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface InterfaceApi {

    @GET(value = "search/users")
    @Headers("Authorization: token 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")
    fun searchUser(@Query("q") query: String): Call<ResponseJson>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DataDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun followersTab(@Path("username") username: String): Call<ArrayList<DataUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun followingTab(@Path("username") username: String): Call<ArrayList<DataUser>>
}