package com.plame.megakiwi.features.items.data

import com.google.gson.JsonElement
import retrofit2.http.GET

interface ItemsApiService {

    @GET("items")
    suspend fun getItems(): JsonElement
}