package com.wonder.feature.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable

const val onboardingRoute = "onboarding"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(onboardingRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onboardingScreen(
    navController: NavHostController
) {
    composable(route = onboardingRoute) {
        OnboardingView()
    }
}
