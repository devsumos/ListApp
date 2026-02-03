package com.example.listapp.data.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
)