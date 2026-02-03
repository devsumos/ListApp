package com.example.listapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapp.data.repo.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val allItems = repository.getAllItems()
                val items = allItems.toItemDetailsList()
                _state.update {
                    it.copy(
                        showError = false,
                        items = items,
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        showError = true,
                    )
                }

            } finally {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    data class State(
        val items: List<ItemDetails>? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}