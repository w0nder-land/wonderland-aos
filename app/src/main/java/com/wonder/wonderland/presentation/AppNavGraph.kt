package com.wonder.wonderland.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wonder.feature.festival.festivalScreen
import com.wonder.component.navigation.Navigate
import com.wonder.feature.onboarding.onboardingScreen
import com.wonder.feature.splash.splashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AppNavGraph(mainViewModel: MainViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Navigate.Screen.Splash.route,
    ) {
        splashScreen(navController = navController)
        onboardingScreen(navController = navController)
        mainScreen(navController = navController)
        festivalScreen(navController = navController)
    }
}
