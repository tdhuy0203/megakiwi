package com.plame.megakiwi.features.rune_list.di

import com.plame.megakiwi.features.rune_list.data.RuneApiService
import com.plame.megakiwi.features.rune_list.data.RuneRepositoryImpl
import com.plame.megakiwi.features.rune_list.domain.repositories.RuneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RuneModule {

    @Provides
    @Singleton
    fun provideRuneApiService(retrofit: Retrofit): RuneApiService {
        return retrofit.create(RuneApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRuneRepository(runeApiService: RuneApiService): RuneRepository {
        return RuneRepositoryImpl(runeApiService)
    }
}