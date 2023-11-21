package com.example.mapin.network.service

import com.example.mapin.network.model.DetailResponse
import com.example.mapin.network.model.PostAllResponse
import com.example.mapin.network.model.PostDetailResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PostService {
    @GET("/post/search/all")
    fun searchAll(
        @Header("Authorization") authorization: String,
    ): Call<PostAllResponse>

    @GET("/post/search/id")
    fun search(
        @Header("Authorization") authorization: String,
        @Query("id") id : String
    ): Call<PostDetailResponse>

    companion object{
        private const val BASE_URL = "http://hohoseung.shop/"

        private val gson =
            GsonBuilder()
                .setLenient()
                .create()
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        fun create() : PostService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(PostService::class.java)
        }
    }
}