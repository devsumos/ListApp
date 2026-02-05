package com.example.listapp

import com.example.listapp.data.local.Item
import com.example.listapp.data.repo.ItemRepository
import com.example.listapp.ui.viewmodel.HomeViewModel
import com.example.listapp.ui.viewmodel.toItemDetails
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private val itemRepository: ItemRepository = mock()

    private val items = listOf(
        Item(
            id = 0,
            userId = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit" +
                    "suscipit recusandae consequuntur expedita et cum" +
                    "reprehenderit molestiae ut ut quas totam" +
                    "nostrum rerum est autem sunt rem eveniet architecto"
        ),
        Item(
            id = 1,
            userId = 2,
            title = "qui est esse",
            body = "est rerum tempore vitae " +
                    "sequi sint nihil reprehenderit dolor beatae ea dolores neque " +
                    "fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis " +
                    "qui aperiam non debitis possimus qui neque nisi nulladsfgsrhtyw4tegsd"
        )
    )

    @Before
    fun setUp() {
        whenever(itemRepository.observeAllItems()).doReturn(flowOf(items))
        viewModel = HomeViewModel(itemRepository)
    }

    @Test
    fun `on init updates state with items`() = runTest {
        assertFalse(viewModel.state.value.isLoading)
        items.forEach {
            assert(
                viewModel.state.value.items?.contains(
                    it.toItemDetails()
                ) == true
            )
        }
    }
}