package com.plame.megakiwi.features.champions.ui.champion_detail

import com.google.gson.JsonObject

data class ChampionDetailUiState(
    val detail: JsonObject = JsonObject(),
    val loading: Boolean = true
)