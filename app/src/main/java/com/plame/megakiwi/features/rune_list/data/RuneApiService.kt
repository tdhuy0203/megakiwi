package com.plame.megakiwi.features.rune_list.data

import com.google.gson.JsonElement
import retrofit2.http.GET

interface RuneApiService {

    @GET("rune-styles")
    suspend fun getRunes(): JsonElement
}