package com.plame.megakiwi.features.champions.ui.champion_list

import com.google.gson.JsonObject

data class ChampionListUiState (
    val championList: MutableList<JsonObject> = ArrayList(),
    val loading: Boolean = true,
)