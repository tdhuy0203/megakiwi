package com.plame.megakiwi.features.items.domain.repositories

import com.google.gson.JsonElement
import com.google.gson.JsonObject

interface ItemsRepository {

    suspend fun getItems(): JsonElement

    suspend fun getItemDetail(itemId: String): JsonObject?
}