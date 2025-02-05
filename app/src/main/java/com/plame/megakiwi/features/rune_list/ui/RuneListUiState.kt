package com.plame.megakiwi.features.rune_list.ui

import com.google.gson.JsonObject

data class RuneListUiState(
    val runeList: MutableList<JsonObject> = ArrayList(),
    val loading: Boolean = true,
)
