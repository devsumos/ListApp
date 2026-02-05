package com.example.listapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapp.data.repo.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _state = MutableStateFlow(
        State()
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            repository.loadAndSaveAllItems()
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.observeAllItems()
                    .distinctUntilChanged()
                    .map { it?.toItemDetailsList() }
                    .collect { items ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                items = items,
                            )
                        }
                    }
            } catch (_: Exception) {
            } finally {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onItemDelete(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemById(itemId)
        }
    }

    data class State(
        val items: List<ItemDetails>? = null,
        val isLoading: Boolean = false,
    )
}