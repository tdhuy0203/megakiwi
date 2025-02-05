package com.plame.megakiwi.features.summoner_spell_list.ui

import com.google.gson.JsonObject

data class SummonerSpellListUiState(
    val summonerSpellList: MutableList<JsonObject> = ArrayList(),
    val loading: Boolean = true,
)