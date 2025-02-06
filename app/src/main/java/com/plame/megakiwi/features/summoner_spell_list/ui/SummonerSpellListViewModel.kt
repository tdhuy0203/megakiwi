package com.plame.megakiwi.features.summoner_spell_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.plame.megakiwi.features.summoner_spell_list.domain.usecases.GetSummonersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummonerSpellListViewModel @Inject constructor(
    private val getSummonersUseCase: GetSummonersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SummonerSpellListUiState())
    val uiState: StateFlow<SummonerSpellListUiState> = _uiState.asStateFlow()

    init {
        fetchSummonerSpellList()
    }

    private fun fetchSummonerSpellList() {
        viewModelScope.launch {
            val res = getSummonersUseCase()
            val summonerSpellList = mutableListOf<JsonObject>()
            res.asJsonObject["data"].asJsonArray.forEach {
                summonerSpellList.add(it.asJsonObject)
            }
            _uiState.update {  state ->
                state.copy(
                    summonerSpellList = summonerSpellList,
                    loading = false
                )
            }
        }
    }
}