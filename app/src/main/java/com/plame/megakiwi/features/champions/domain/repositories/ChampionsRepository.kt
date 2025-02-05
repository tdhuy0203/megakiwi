package com.plame.megakiwi.features.champions.domain.repositories

import com.google.gson.JsonElement

interface ChampionsRepository {

    suspend fun getChampions(): JsonElement

    suspend fun getChampionDetail(alias: String): JsonElement
}