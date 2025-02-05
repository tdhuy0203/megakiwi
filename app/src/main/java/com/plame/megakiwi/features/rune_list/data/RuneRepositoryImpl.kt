package com.plame.megakiwi.features.rune_list.data

import com.google.gson.JsonElement
import com.plame.megakiwi.features.rune_list.domain.repositories.RuneRepository
import javax.inject.Inject

class RuneRepositoryImpl @Inject constructor(
    private val runeApiService: RuneApiService
) : RuneRepository {

    override suspend fun getRunes(): JsonElement {
        return runeApiService.getRunes()
    }
}