package com.plame.megakiwi.features.summoner_spell_list.di

import com.plame.megakiwi.features.summoner_spell_list.data.SummonerSpellApiService
import com.plame.megakiwi.features.summoner_spell_list.data.SummonerSpellRepositoryImpl
import com.plame.megakiwi.features.summoner_spell_list.domain.repositories.SummonerSpellRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SummonerSpellModule {

    @Provides
    @Singleton
    fun provideSummonerSpellApiService(retrofit: Retrofit): SummonerSpellApiService {
        return retrofit.create(SummonerSpellApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSummonerSpellRepository(
        summonerSpellApiService: SummonerSpellApiService
    ): SummonerSpellRepository {
        return SummonerSpellRepositoryImpl(summonerSpellApiService)
    }
}