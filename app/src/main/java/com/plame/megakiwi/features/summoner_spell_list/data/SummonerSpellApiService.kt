package com.plame.megakiwi.features.summoner_spell_list.data

import com.google.gson.JsonElement
import retrofit2.http.GET

interface SummonerSpellApiService {

    @GET("summoners")
    suspend fun getSummoners(): JsonElement

}