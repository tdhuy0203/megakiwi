package com.plame.megakiwi.features.champions.domain.usecases

import com.google.gson.JsonElement
import com.plame.megakiwi.features.champions.domain.repositories.ChampionsRepository
import javax.inject.Inject

class GetChampionDetailUseCase @Inject constructor(
    private val championsRepository: ChampionsRepository
) {
    suspend operator fun invoke(alias: String): JsonElement {
        return championsRepository.getChampionDetail(alias)
    }
}