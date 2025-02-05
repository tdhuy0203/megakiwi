package com.plame.megakiwi.features.summoner_spell_list.data

import com.google.gson.JsonElement
import com.plame.megakiwi.features.summoner_spell_list.domain.repositories.SummonerSpellRepository
import javax.inject.Inject

class SummonerSpellRepositoryImpl @Inject constructor(
    private val summonerSpellApiService: SummonerSpellApiService
) : SummonerSpellRepository {

    override suspend fun getSummoners(): JsonElement {
        return summonerSpellApiService.getSummoners()
    }
}