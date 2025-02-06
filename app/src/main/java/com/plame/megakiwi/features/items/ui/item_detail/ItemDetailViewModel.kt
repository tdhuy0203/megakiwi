package com.plame.megakiwi.features.items.ui.item_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.plame.megakiwi.core.utils.Globals
import com.plame.megakiwi.features.items.domain.usecases.GetItemDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getItemDetailUseCase: GetItemDetailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemDetailUiState())
    val uiState: StateFlow<ItemDetailUiState> = _uiState.asStateFlow()
    private val _itemId = checkNotNull(savedStateHandle["item_id"])

    init {
        getDetail()
    }

    private fun getDetail() {
        viewModelScope.launch {
            val item = getItemDetailUseCase(_itemId as String)
            _uiState.update { state ->
                state.copy(detail = item ?: JsonObject())
            }
        }
    }
}