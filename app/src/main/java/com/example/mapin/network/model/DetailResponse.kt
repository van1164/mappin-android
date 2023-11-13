package com.example.mapin.network.model

import com.example.mapin.data.model.MainListItem

data class DetailResponse(
    val statusCode : String,
    val isSuccess : String,
    val title: String,
    val content: String,
    val foundDate: String,
    val createdAt: String,
    val image: String,
    val x: Double,
    val y: Double,
    val dong: String,
    val category: String,
)
