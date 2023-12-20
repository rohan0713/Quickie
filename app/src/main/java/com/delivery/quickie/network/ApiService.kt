package com.delivery.quickie.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiService {

//    @GET("api/images/{imageId}")
//    @Streaming
//    fun getImage(@Path("imageId") imageId: String): Call<ResponseBody>

    @Multipart
    @POST("upload")
    fun uploadImage(@Part image: MultipartBody.Part, @Part("email") email: RequestBody): Call<ImageResponse>

    @Multipart
    @POST("upload/profile")
    fun uploadProfileImage(@Part image: MultipartBody.Part, @Part("email") email: RequestBody,
                           @Part("username") username: RequestBody): Call<ImageResponse>

    //get Profile image "url/api/images/profile/image_id"
    //get Posts image "url/api/images/image_id"

    @GET("authenticate")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserResponse>

    @POST("create")
    suspend fun createUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserResponse>

    @GET("getProfile")
    fun getProfile(
        @Query("email") email: String
    ) : Call<ProfileResponse>
}