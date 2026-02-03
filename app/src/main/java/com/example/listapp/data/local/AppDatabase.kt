package com.example.listapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val dbName = "Main.db"

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                dbName
            ).build()
    }
}