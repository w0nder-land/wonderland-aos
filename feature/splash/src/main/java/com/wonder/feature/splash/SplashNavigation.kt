package com.wonder.feature.splash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigate.navigateWithPopBackStack
import com.wonder.component.navigate.onboardingRoute
import com.wonder.component.navigate.splashRoute

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    navigate(splashRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashScreen(
    navController: NavHostController
) {
    composable(route = splashRoute) {
        SplashView(
            onMoveOnboarding = {
                navController.navigateWithPopBackStack(onboardingRoute)
            }
        )
    }
}
