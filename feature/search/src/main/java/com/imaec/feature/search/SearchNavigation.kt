package com.imaec.feature.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.Search.route) {
        SearchView(
            onBackClick = { mainNavController.popBackStack() }
        )
    }
}
