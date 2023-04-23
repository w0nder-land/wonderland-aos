package com.wonder.wonderland.presentation.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate
import com.wonder.wonderland.presentation.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.Search.route) { entry ->
        val parentEntry: NavBackStackEntry = remember(entry) {
            navController.getBackStackEntry(Navigate.Screen.Main.route)
        }
        val mainViewModel: MainViewModel = hiltViewModel(parentEntry)

        SearchView(
            mainViewModel = mainViewModel,
            onBackClick = { mainNavController.popBackStack() }
        )
    }
}
