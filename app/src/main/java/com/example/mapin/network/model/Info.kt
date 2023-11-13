package com.example.mapin.network.model

import java.io.Serializable

data class Info(
    val category: String,
    val content: String,
    val dong: String,
    val foundDate: String,
    val title: String,
    val x: Double,
    val y: Double
):Serializable