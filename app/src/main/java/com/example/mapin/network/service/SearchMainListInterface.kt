package com.example.mapin.network.service

import com.example.mapin.network.model.MainListResponse
import com.example.mapin.network.model.SearchLocationResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchMainListInterface {
    @GET("/lost/search/location")
    fun searchList(
        @Header("Authorization") authorization: String,
        @Query("x") x: String,
        @Query("y") y: String
    ): Call<MainListResponse>


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

        fun create() : SearchMainListInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SearchMainListInterface::class.java)
        }
    }
}