package com.example.mapin.network.service

import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface CreateService {
    @Multipart
    @PUT("/lost/register")
    fun create(
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Part("info") info: RequestBody
    ): Call<Any>


    companion object {
        private const val BASE_URL = "http://hohoseung.shop/"

        private val gson =
            GsonBuilder()
                .setLenient()
                .create()
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS).build()

        fun create(): CreateService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(CreateService::class.java)
        }
    }
}