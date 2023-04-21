package com.wonder.feature.splash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate
import com.wonder.component.navigation.navigateWithPopBackStack

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    navigate(Navigate.Screen.Splash.route, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashScreen(
    navController: NavHostController
) {
    composable(route = Navigate.Screen.Splash.route) {
        SplashView(
            onMoveOnboarding = {
                navController.navigateWithPopBackStack(Navigate.Screen.Onboarding.route)
            }
        )
    }
}
