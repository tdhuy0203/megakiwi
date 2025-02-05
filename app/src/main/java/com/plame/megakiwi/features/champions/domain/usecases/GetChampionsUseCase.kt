package com.plame.megakiwi.features.champions.domain.usecases

import com.google.gson.JsonElement
import com.plame.megakiwi.features.champions.domain.repositories.ChampionsRepository
import javax.inject.Inject

class GetChampionsUseCase @Inject constructor(
    private val championsRepository: ChampionsRepository
) {
    suspend operator fun invoke(): JsonElement {
        return championsRepository.getChampions()
    }
}