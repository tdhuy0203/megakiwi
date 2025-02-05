package com.plame.megakiwi.features.champions.data

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Path

interface ChampionsApiService {

    @GET("champions")
    suspend fun getChampions(): JsonElement

    @GET("champions/{alias}")
    suspend fun getChampionDetail(
        @Path("alias") alias: String
    ): JsonElement

}