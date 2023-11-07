package com.example.mapin.network.service


import com.example.mapin.network.model.SearchLocationResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchLocationService {

    @GET("/search/dong/{dong_name}")
    fun search(
        @Header("Authorization") authorization: String,
        @Query("dong_name") dong_name: String
    ): Call<List<SearchLocationResponse>>


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

        fun create() : SearchLocationService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SearchLocationService::class.java)
        }
    }
}