package com.wonder.feature.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate
import com.wonder.component.navigation.navigateWithPopBackStack

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(Navigate.Screen.Onboarding.route, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onboardingScreen(
    navController: NavHostController
) {
    composable(route = Navigate.Screen.Onboarding.route) {
        OnboardingView(
            onMoveMain = { navController.navigateWithPopBackStack(Navigate.Screen.Main.route) }
        )
    }
}
