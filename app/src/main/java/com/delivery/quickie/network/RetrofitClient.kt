package com.delivery.quickie.network

import com.delivery.quickie.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {

        private val retrofit: Retrofit by lazy {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val db: Retrofit by lazy {

            Retrofit.Builder()
                .baseUrl(Constants.dbUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val dbApi: ApiService by lazy {
            db.create(ApiService::class.java)
        }

        val api: Posts by lazy {
            retrofit.create(Posts::class.java)
        }

    }
}