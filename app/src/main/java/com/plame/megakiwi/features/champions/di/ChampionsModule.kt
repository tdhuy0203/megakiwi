package com.plame.megakiwi.features.champions.di

import com.plame.megakiwi.features.champions.data.ChampionsApiService
import com.plame.megakiwi.features.champions.data.ChampionsRepositoryImpl
import com.plame.megakiwi.features.champions.domain.repositories.ChampionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChampionsModule {

    @Provides
    @Singleton
    fun provideChampionsApiService(retrofit: Retrofit): ChampionsApiService {
        return retrofit.create(ChampionsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChampionsRepository(championsApiService: ChampionsApiService): ChampionsRepository {
        return ChampionsRepositoryImpl(championsApiService)
    }
}