package com.wonder.feature.my

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.myScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.My.route) {
        MyView(
            onBackClick = { mainNavController.popBackStack() }
        )
    }
}
