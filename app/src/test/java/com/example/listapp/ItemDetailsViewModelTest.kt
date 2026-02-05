package com.example.listapp

import com.example.listapp.data.local.Item
import com.example.listapp.data.repo.ItemRepository
import com.example.listapp.ui.viewmodel.ItemDetailsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ItemDetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ItemDetailsViewModel

    private val itemRepository: ItemRepository = mock {
        onBlocking { getItemById(any()) } doReturn Item(
            id = 0,
            userId = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit suscipit recusandae consequuntur expedita et cum"
        )
    }

    private val itemId = 0

    @Before
    fun setUp() {
        viewModel = ItemDetailsViewModel(
            itemId = itemId,
            repository = itemRepository,
        )
    }

    @Test
    fun `on init updates state correctly`() = runTest {
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(viewModel.state.value.itemId, 0)
        assertEquals(
            viewModel.state.value.itemTitle,
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        )
        assertEquals(
            viewModel.state.value.itemBody,
            "quia et suscipit suscipit recusandae consequuntur expedita et cum"
        )
        assertEquals(viewModel.state.value.userId, 1)
    }

    @Test
    fun `on save`() = runTest {
        viewModel.onTitleChange("title")
        viewModel.onBodyChange("body")
        viewModel.onSave()
        assertEquals(
            viewModel.state.value.itemTitle,
            "title"
        )
        assertEquals(
            viewModel.state.value.itemBody,
            "body"
        )
        assertEquals(
            viewModel.state.value.navigateBack,
            true
        )
        verify(itemRepository).updateItem(
            Item(
                id = 0,
                userId = 1,
                title = "title",
                body = "body"
            )
        )
    }

}