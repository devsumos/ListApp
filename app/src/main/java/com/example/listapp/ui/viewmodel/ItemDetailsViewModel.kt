package com.example.listapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapp.data.repo.ItemRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel(assistedFactory = ItemDetailsViewModel.Factory::class)
class ItemDetailsViewModel @AssistedInject constructor(
    @Assisted private val itemId: Int = -1,
    private val repository: ItemRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            try {

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

    @AssistedFactory
    interface Factory {
        fun create(
            itemId: Int,
        ): ItemDetailsViewModel
    }

    data class State(
        val itemDetails: ItemDetails? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
    )
}