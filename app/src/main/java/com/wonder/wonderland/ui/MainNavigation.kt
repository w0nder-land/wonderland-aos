package com.wonder.wonderland.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigate.mainRoute

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(mainRoute, navOptions)
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
