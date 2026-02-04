package com.example.listapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.listapp.ui.screen.HomeScreen
import com.example.listapp.ui.screen.ItemDetailsScreen
import com.example.listapp.ui.theme.ListAppTheme
import com.example.listapp.ui.viewmodel.HomeViewModel
import com.example.listapp.ui.viewmodel.ItemDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                ) {
                    composable(route = Screen.HomeScreen.route) {
                        val viewModel = hiltViewModel<HomeViewModel>()
                        val state = viewModel.state.collectAsStateWithLifecycle().value
                        HomeScreen(
                            items = state.items,
                            isLoading = state.isLoading,
                            onItemClick = { itemId ->
                                navController.navigate(
                                    route = Screen.ItemDetailsScreen.route + "?itemId=${itemId}"
                                )
                            },
                            onItemDelete = viewModel::onItemDelete
                        )
                    }
                    composable(
                        route = Screen.ItemDetailsScreen.route + "?itemId={itemId}",
                        arguments = listOf(
                            navArgument("itemId") {
                                type = NavType.IntType
                                nullable = false
                            },
                        )
                    ) {
                        val viewModel =
                            hiltViewModel<ItemDetailsViewModel, ItemDetailsViewModel.Factory> { factory ->
                                factory.create(
                                    itemId = it.arguments?.getInt("itemId") ?: -1
                                )
                            }
                        val state = viewModel.state.collectAsStateWithLifecycle().value
                        ItemDetailsScreen(
                            itemTitle = state.itemTitle,
                            itemId = state.itemId,
                            itemBody = state.itemBody,
                            isLoading = state.isLoading,
                            onTitleChange = viewModel::onTitleChange,
                            onBodyChange = viewModel::onBodyChange,
                            onSave = viewModel::onSave,
                        )
                        LaunchedEffect(state.navigateBack) {
                            if (state.navigateBack) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object ItemDetailsScreen : Screen("item_details_screen")
}