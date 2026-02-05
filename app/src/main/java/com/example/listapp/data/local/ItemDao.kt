package com.example.listapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun observeAllItems(): Flow<List<Item>?>

    @Query("SELECT * FROM item WHERE id LIKE :id LIMIT 1")
    suspend fun getItemById(id: Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)
    @Update
    suspend fun update(item: Item)

    @Query("DELETE FROM item WHERE id LIKE :itemId")
    suspend fun deleteItemById(itemId: Int)
}