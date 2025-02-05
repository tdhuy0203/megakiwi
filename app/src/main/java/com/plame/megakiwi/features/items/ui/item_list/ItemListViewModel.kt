package com.plame.megakiwi.features.items.ui.item_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.plame.megakiwi.core.utils.Globals
import com.plame.megakiwi.features.items.domain.usecases.GetItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListUiState())

    val uiState: StateFlow<ItemListUiState> = _uiState.asStateFlow()

    init {
        fetchItemList()
    }

    private fun fetchItemList() {
        viewModelScope.launch {
            val result = getItemsUseCase()
            val array = result.asJsonObject.get("data").asJsonArray
            val itemList = mutableListOf<JsonObject>()
            val itemMap = mutableMapOf<String, JsonObject>()
            array.forEach {
                itemList.add(it.asJsonObject)
                itemMap[it.asJsonObject["id"].asString] = it.asJsonObject
            }
            Globals.items = itemMap
            _uiState.update {  state ->
                state.copy(
                    itemList = itemList,
                    loading = false
                )
            }
        }
    }
}