package com.plame.megakiwi.features.rune_list.domain.usecases

import com.google.gson.JsonElement
import com.plame.megakiwi.features.rune_list.domain.repositories.RuneRepository
import javax.inject.Inject

class GetRunesUseCase @Inject constructor(
    private val runeRepository: RuneRepository
) {

    suspend operator fun invoke(): JsonElement {
        return runeRepository.getRunes()
    }
}