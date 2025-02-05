package com.plame.megakiwi.features.items.ui.item_list

import com.google.gson.JsonObject

data class ItemListUiState(
    val itemList: MutableList<JsonObject> = ArrayList(),
    val loading: Boolean = true,
)
