package com.example.mapin.network.model

data class SearchLocationResponse(
    val statusCode: Int,
    val isSuccess: String,
    val losts : List<LocationItem>,
)

data class LocationItem(
    val id: Int,
    val title: String,
    val createdAt: String,
    val imageUrl: String,
    val dong: String,
)

data class SearchCategoryResponse(
    val statusCode: Int,
    val isSuccess: String,
    val losts : List<CategoryItem>,

)

data class CategoryItem(
    val id: Int,
    val title: String,
    val createdAt: String,
    val imageUrl: String,
    val dong: String
)

data class SearchShopResponse(
    val statusCode: Int,
    val isSuccess: String,
    val losts : List<ShopItem>,

)

data class ShopItem(
    val id: Int,
    val title: String,
    val createdAt: String,
    val imageUrl: String,
    val shopName: String,
    val foundDate : String
)