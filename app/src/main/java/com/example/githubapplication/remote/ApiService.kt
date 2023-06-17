package com.example.githubapplication.remote

import com.example.githubapplication.GithubResponse
import com.example.githubapplication.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun searchAccount(
        @Query("q") username : String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailAccount(@Path("username") username: String): Call<ItemsItem>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}