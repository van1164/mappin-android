package com.example.mapin.network.model

data class PostAllResponse(
    val isSuccess: String,
    val posts: List<Post>,
    val statusCode: Int
)