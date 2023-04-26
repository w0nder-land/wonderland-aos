package com.imaec.feature.festival

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

fun NavController.navigateToFestival(navOptions: NavOptions? = null) {
    navigate(Navigate.Screen.Festival.route, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.festivalScreen(
    navController: NavHostController
) {
    composable(route = Navigate.Screen.Festival.route) {
        FestivalView(
            onBack = { navController.popBackStack() }
        )
    }
}
