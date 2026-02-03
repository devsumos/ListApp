package com.example.listapp.data.repo

import com.example.listapp.data.remote.ListApi
import com.example.listapp.data.remote.ListItemResponse
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: ListApi
) {
    suspend fun getAllItems(): List<ListItemResponse> {
        return api.getItemList()
    }
}
