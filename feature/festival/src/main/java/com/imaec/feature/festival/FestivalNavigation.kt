package com.imaec.feature.festival

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate

fun NavController.navigateToFestival(
    festivalId: Int = -1,
    navOptions: NavOptions? = null
) {
    navigate(Navigate.Screen.Festival.route + "/$festivalId", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.festivalScreen(
    navController: NavHostController
) {
    composable(
        route = Navigate.Screen.Festival.route + "/{festivalId}",
        arguments = listOf(
            navArgument("festivalId") {
                type = NavType.IntType
            }
        )
    ) {
        FestivalView(
            onBack = { navController.popBackStack() }
        )
    }
}
