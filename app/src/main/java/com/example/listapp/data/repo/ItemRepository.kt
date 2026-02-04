package com.example.listapp.data.repo

import com.example.listapp.data.local.AppDatabase
import com.example.listapp.data.local.Item
import com.example.listapp.data.remote.ListApi
import com.example.listapp.data.remote.toItemEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val api: ListApi,
    database: AppDatabase,
) {
    val itemDao = database.itemDao()

    suspend fun loadAndSaveAllItems() {
        api.getItemList().forEach { itemDao.insert(it.toItemEntity()) }
    }

    fun observeAllItems() = itemDao.observeAllItems()

    suspend fun getItemById(id: Int) = itemDao.getItemById(id)

    suspend fun updateItem(item: Item) = itemDao.update(item)

    suspend fun deleteItemById(itemId: Int) = itemDao.deleteItemById(itemId)
}
