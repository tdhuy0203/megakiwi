package com.plame.megakiwi.features.items.domain.usecases

import com.google.gson.JsonElement
import com.plame.megakiwi.features.items.domain.repositories.ItemsRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository
) {
    suspend operator fun invoke(): JsonElement {
        return itemsRepository.getItems()
    }
}