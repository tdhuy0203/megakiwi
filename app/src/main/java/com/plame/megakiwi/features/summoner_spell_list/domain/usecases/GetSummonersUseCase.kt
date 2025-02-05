package com.plame.megakiwi.features.summoner_spell_list.domain.usecases

import com.google.gson.JsonElement
import com.plame.megakiwi.features.summoner_spell_list.domain.repositories.SummonerSpellRepository
import javax.inject.Inject

class GetSummonersUseCase @Inject constructor(
    private val summonerSpellRepository: SummonerSpellRepository
) {
    suspend operator fun invoke(): JsonElement {
        return summonerSpellRepository.getSummoners()
    }
}