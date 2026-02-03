package com.example.listapp.ui.viewmodel

import com.example.listapp.data.local.Item

data class ItemDetails(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)

fun Item.toItemDetails() = ItemDetails(
    userId = this.userId,
    id = this.id,
    title = this.title,
    body = this.body,
)

fun List<Item>.toItemDetailsList(): List<ItemDetails> {
    val list = mutableListOf<ItemDetails>()
    this.forEach {
        list.add(it.toItemDetails())
    }
    return list
}