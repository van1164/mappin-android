package com.example.mapin.network.model

import com.example.mapin.data.model.MainListItem

data class MainListResponse(
    val statusCode : String,
    val isSuccess : String,
    val losts : List<MainListItem>

)
