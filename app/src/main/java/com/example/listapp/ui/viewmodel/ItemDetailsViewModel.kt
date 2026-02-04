package com.example.listapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listapp.data.local.Item
import com.example.listapp.data.repo.ItemRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


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
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val item = repository.getItemById(itemId)
                item?.let {
                    _state.update {
                        it.copy(
                            itemId = item.id,
                            userId = item.userId,
                            itemTitle = item.title,
                            itemBody = item.body,
                        )
                    }
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        navigateBack = true,
                    )
                }

            } finally {
                _state.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onSave() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(
                Item(
                    id = itemId,
                    userId = state.value.userId,
                    title = state.value.itemTitle,
                    body = state.value.itemBody,
                )
            )
        }
        _state.update {
            it.copy(navigateBack = true)
        }
    }

    fun onTitleChange(title: String) {
        _state.update {
            it.copy(itemTitle = title)
        }
    }

    fun onBodyChange(body: String) {
        _state.update {
            it.copy(itemBody = body)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            itemId: Int,
        ): ItemDetailsViewModel
    }

    data class State(
        val itemId: Int? = null,
        val userId: Int = 0,
        val itemTitle: String = "",
        val itemBody: String = "",
        val isLoading: Boolean = false,
        val navigateBack: Boolean = false,
        val isSavingInProgress: Boolean = false,
    )
}