package com.example.mapin.network.service


import com.example.mapin.network.model.SearchCategoryResponse
import com.example.mapin.network.model.SearchLocationResponse
import com.example.mapin.network.model.SearchShopResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchLocationService {

    @GET("/lost/search/dong")
    fun search(
        @Header("Authorization") authorization: String,
        @Query("name") dong_name: String
    ): Call<SearchLocationResponse>


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

interface SearchCategoryService {

    @GET("/lost/search/category")
    fun search(
        @Header("Authorization") authorization: String,
        @Query("category") category: String
    ): Call<SearchCategoryResponse>


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

        fun create() : SearchCategoryService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SearchCategoryService::class.java)
        }
    }
}

interface SearchShopService {

    @GET("/lost/search/shop")
    fun search(
        @Header("Authorization") authorization: String,
        @Query("name") shop: String
    ): Call<SearchShopResponse>


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

        fun create() : SearchShopService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(SearchShopService::class.java)
        }
    }
}