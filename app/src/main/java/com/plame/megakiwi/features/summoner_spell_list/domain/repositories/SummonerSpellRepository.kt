package com.plame.megakiwi.features.summoner_spell_list.domain.repositories

import com.google.gson.JsonElement

interface SummonerSpellRepository {

    suspend fun getSummoners(): JsonElement
}