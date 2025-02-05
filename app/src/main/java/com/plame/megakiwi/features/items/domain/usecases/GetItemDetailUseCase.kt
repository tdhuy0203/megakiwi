package com.plame.megakiwi.features.items.domain.usecases

import com.google.gson.JsonObject
import com.plame.megakiwi.features.items.domain.repositories.ItemsRepository
import javax.inject.Inject

class GetItemDetailUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository
) {
    suspend operator fun invoke(itemId: String): JsonObject? {
        return itemsRepository.getItemDetail(itemId)
    }
}