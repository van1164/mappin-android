package com.example.mapin.network.service

import com.example.mapin.network.model.LoginResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(
    val email :String,
    val role : String,
    val name : String
)

interface LoginService {
    @POST("/login")
    fun login(
        @Body loginRequest : LoginRequest,
    ) : Call<LoginResponse>

    companion object{
        private const val BASE_URL = "http://hohoseung.shop/"

        private val gson =
            GsonBuilder()
                .setLenient()
                .create()

        fun create() : LoginService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LoginService::class.java)
        }
    }
}