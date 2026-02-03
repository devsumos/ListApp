package com.example.listapp.data.remote

import com.example.listapp.data.local.Item

data class ListItemResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)

fun ListItemResponse.toItemEntity() = Item(
    userId = this.userId,
    id = this.id,
    title = this.title,
    body = this.body,
)
