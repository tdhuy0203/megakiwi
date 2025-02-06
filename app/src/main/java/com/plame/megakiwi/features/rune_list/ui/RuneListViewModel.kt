package com.plame.megakiwi.features.rune_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.plame.megakiwi.features.rune_list.domain.usecases.GetRunesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RuneListViewModel @Inject constructor(
    private val getRunesUseCase: GetRunesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow((RuneListUiState()))
    val uiState: StateFlow<RuneListUiState> = _uiState.asStateFlow()

    init {
        fetchRuneList()
    }

    private fun fetchRuneList() {
        viewModelScope.launch {
            val res = getRunesUseCase()
            val runeList = mutableListOf<JsonObject>()
            res.asJsonObject["data"].asJsonArray.forEach { rune ->
                runeList.add(rune.asJsonObject)
            }
            _uiState.update { state ->
                state.copy(
                    runeList = runeList,
                    loading = false
                )
            }
        }
    }
}