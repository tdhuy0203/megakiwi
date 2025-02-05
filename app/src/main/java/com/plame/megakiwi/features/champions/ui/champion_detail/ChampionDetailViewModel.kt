package com.plame.megakiwi.features.champions.ui.champion_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plame.megakiwi.features.champions.domain.usecases.GetChampionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getChampionDetailUseCase: GetChampionDetailUseCase
) : ViewModel() {

    private val _championAlias: String = checkNotNull(savedStateHandle["champion_alias"])
    private val _uiState = MutableStateFlow(ChampionDetailUiState())
    val uiState: StateFlow<ChampionDetailUiState> = _uiState.asStateFlow()

    init {
        getDetail()
    }

    private fun getDetail() {
        viewModelScope.launch {
            val res = getChampionDetailUseCase(_championAlias)
            val detail = res.asJsonObject["data"].asJsonObject
            _uiState.update { state ->
                state.copy(
                    detail = detail,
                    loading = false
                )
            }
        }
    }
}