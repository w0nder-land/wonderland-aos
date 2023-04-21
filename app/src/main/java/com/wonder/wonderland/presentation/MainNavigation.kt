package com.wonder.wonderland.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(Navigate.Screen.Main.route, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainScreen(
    navController: NavHostController
) {
    composable(route = Navigate.Screen.Main.route) {
        MainView(
            navController = navController
        )
    }
}
