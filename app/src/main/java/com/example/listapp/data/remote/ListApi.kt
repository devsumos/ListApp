package com.example.listapp.data.remote

import retrofit2.http.GET

interface ListApi {

    @GET("/posts")
    suspend fun getItemList(): List<ListItemResponse>
}