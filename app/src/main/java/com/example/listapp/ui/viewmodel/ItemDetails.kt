package com.example.listapp.ui.viewmodel

import com.example.listapp.data.remote.ListItemResponse

data class ItemDetails(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)

fun ListItemResponse.toItemDetails() = ItemDetails(
    userId = this.userId,
    id = this.id,
    title = this.title,
    body = this.body,
)

fun List<ListItemResponse>.toItemDetailsList(): List<ItemDetails> {
    val list = mutableListOf<ItemDetails>()
    this.forEach {
        list.add(it.toItemDetails())
    }
    return list
}