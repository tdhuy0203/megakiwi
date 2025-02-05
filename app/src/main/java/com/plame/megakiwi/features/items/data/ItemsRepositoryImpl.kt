package com.plame.megakiwi.features.items.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.plame.megakiwi.core.utils.Globals
import com.plame.megakiwi.features.items.domain.repositories.ItemsRepository
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(
    private val itemsApiService: ItemsApiService
) : ItemsRepository {

    override suspend fun getItems(): JsonElement {
        return itemsApiService.getItems()
    }

    override suspend fun getItemDetail(itemId: String): JsonObject? {
        return Globals.items[itemId]
    }

}