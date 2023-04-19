package com.wonder.wonderland.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wonder.component.theme.WonderTheme

@Composable
fun MainView(
    navController: NavHostController
) {
    MainScreen(
        navController = navController
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    navController: NavHostController
) {
    val mainNavController = rememberAnimatedNavController()

    Scaffold(
        content = { padding ->
            MainContent(
                modifier = Modifier.padding(padding),
                navController = navController,
                mainNavController = mainNavController
            )
        }
    )
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavHostController,
    mainNavController: NavHostController
) {
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    WonderTheme {
        MainScreen(
            navController = rememberAnimatedNavController()
        )
    }
}
