package com.example.mapin.network.model

data class LoginResponse(
    val expires_In: Int,
    val id: Int,
    val isSuccess: String,
    val jwt: String,
    val role: String,
    val statusCode: Int,
    val token_Type: String
)