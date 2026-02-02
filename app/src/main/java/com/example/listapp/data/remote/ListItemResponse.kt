package com.example.listapp.data.remote

data class ListItemResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)