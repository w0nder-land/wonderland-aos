package com.wonder.wonderland.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable

const val mainRoute = "main"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(mainRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainScreen(
    navController: NavHostController
) {
    composable(route = mainRoute) {
        MainView(
            navController = navController
        )
    }
}
