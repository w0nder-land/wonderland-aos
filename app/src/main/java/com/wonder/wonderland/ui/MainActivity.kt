package com.wonder.wonderland.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wonder.component.navigate.splashRoute
import com.wonder.component.theme.WonderTheme
import com.wonder.feature.onboarding.onboardingScreen
import com.wonder.feature.splash.splashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainNavGraph()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavGraph(mainViewModel: MainViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberAnimatedNavController()

    WonderTheme {
        AnimatedNavHost(
            navController = navController,
            startDestination = splashRoute,
        ) {
            splashScreen(navController = navController)
            onboardingScreen(navController = navController)
            mainScreen(navController = navController)
        }
    }
}
