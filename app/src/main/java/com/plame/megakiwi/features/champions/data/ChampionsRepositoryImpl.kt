package com.plame.megakiwi.features.champions.data

import com.google.gson.JsonElement
import com.plame.megakiwi.features.champions.domain.repositories.ChampionsRepository
import javax.inject.Inject

class ChampionsRepositoryImpl @Inject constructor(
    private val championsApiService: ChampionsApiService
) : ChampionsRepository {

    override suspend fun getChampions(): JsonElement {
        return championsApiService.getChampions()
    }

    override suspend fun getChampionDetail(alias: String): JsonElement {
        return championsApiService.getChampionDetail(alias)
    }
}