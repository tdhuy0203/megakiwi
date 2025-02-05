package com.plame.megakiwi.features.items.di

import com.plame.megakiwi.features.items.data.ItemsApiService
import com.plame.megakiwi.features.items.data.ItemsRepositoryImpl
import com.plame.megakiwi.features.items.domain.repositories.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItemsModule {

    @Provides
    @Singleton
    fun provideItemsApiService(retrofit: Retrofit): ItemsApiService {
        return retrofit.create(ItemsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideItemsRepository(itemsApiService: ItemsApiService): ItemsRepository {
        return ItemsRepositoryImpl(itemsApiService)
    }
}