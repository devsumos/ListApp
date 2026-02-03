package com.example.listapp.di

import com.example.listapp.data.remote.ListApi
import com.example.listapp.data.repo.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideListApi(retrofit: Retrofit): ListApi =
        retrofit.create(ListApi::class.java)

    @Provides
    @Singleton
    fun provideItemRepository(
        api: ListApi
    ): ItemRepository = ItemRepository(api)
}