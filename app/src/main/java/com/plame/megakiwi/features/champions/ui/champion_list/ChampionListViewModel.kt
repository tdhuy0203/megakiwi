package com.plame.megakiwi.features.champions.ui.champion_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.plame.megakiwi.features.champions.domain.usecases.GetChampionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChampionListViewModel @Inject constructor(
    private val getChampionsUseCase: GetChampionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChampionListUiState())

    val uiState: StateFlow<ChampionListUiState> = _uiState.asStateFlow()

    init {
        fetchChampions()
    }

    private fun fetchChampions() {
        viewModelScope.launch {
            val result = getChampionsUseCase()
            val json = result.asJsonObject
            if (json != null) {
                val list = json.getAsJsonArray("data")
                val championList = mutableListOf<JsonObject>()
                list.forEach {
                    championList.add(it.asJsonObject)
                }
                _uiState.update { currentState ->
                    currentState.copy(
                        championList = championList,
                        loading = false
                    )
                }
            }
            print("")
        }
    }
}