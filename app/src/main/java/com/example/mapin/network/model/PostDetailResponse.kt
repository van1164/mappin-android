package com.example.mapin.network.model

data class PostDetailResponse(
    val category: String,
    val content: String,
    val createdAt: String,
    val dong: String,
    val image: String,
    val isSuccess: String,
    val lostDate: String,
    val statusCode: Int,
    val title: String,
    val x: Double,
    val y: Double
)