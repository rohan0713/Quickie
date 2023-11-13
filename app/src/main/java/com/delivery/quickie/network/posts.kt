package com.delivery.quickie.network

import com.delivery.quickie.data.PostsResponse
import retrofit2.Call
import retrofit2.http.GET

interface Posts {

    @GET("feed")
    fun getPosts() : Call<PostsResponse>
}