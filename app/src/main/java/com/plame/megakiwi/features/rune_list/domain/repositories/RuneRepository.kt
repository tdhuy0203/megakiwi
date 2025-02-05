package com.plame.megakiwi.features.rune_list.domain.repositories

import com.google.gson.JsonElement

interface RuneRepository {

    suspend fun getRunes(): JsonElement
}