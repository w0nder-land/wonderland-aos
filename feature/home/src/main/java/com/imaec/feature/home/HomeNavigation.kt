package com.imaec.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.Home.route) {
        HomeView(
            onFestivalClick = {
                navController.navigate(route = Navigate.Screen.Festival.route + "/310")
            }
        )
    }
}
