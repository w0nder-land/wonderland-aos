package com.wonder.wonderland.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wonder.component.navigation.Navigate
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Suit
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.wonderland.presentation.calendar.calendarScreen
import com.wonder.wonderland.presentation.home.homeScreen
import com.wonder.wonderland.presentation.my.myScreen
import com.wonder.wonderland.presentation.search.searchScreen

@Composable
fun MainView(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {
    MainScreen(
        navController = navController,
        currentDestination = mainViewModel.currentNavigation.collectAsStateWithLifecycle().value,
        onBottomNavigationClick = mainViewModel::selectBottomNavigationItem,
    )
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    navController: NavHostController,
    currentDestination: MainDestination,
    onBottomNavigationClick: (destination: MainDestination) -> Unit,
) {
    val mainNavController = rememberAnimatedNavController()

    Scaffold(
        content = { padding ->
            MainContent(
                modifier = Modifier.padding(padding),
                navController = navController,
                mainNavController = mainNavController
            )
        },
        bottomBar = {
            MainBottomBar(
                onBottomNavigationClick = { destination ->
                    onBottomNavigationClick(destination)

                    if (currentDestination != destination) {
                        with(mainNavController) {
                            navigate(
                                route = destination.route,
                                navOptions = navOptions {
                                    graph.startDestinationRoute?.let { startRoute ->
                                        popUpTo(startRoute) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            )
                        }
                    }
                },
                currentDestination = currentDestination
            )
        },
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavHostController,
    mainNavController: NavHostController
) {
    AnimatedNavHost(
        navController = mainNavController,
        startDestination = Navigate.Screen.Home.route,
        enterTransition = {
            fadeIn(
                initialAlpha = 1f,
                animationSpec = tween(durationMillis = 0)
            )
        },
        exitTransition = {
            fadeOut(
                targetAlpha = 0f,
                animationSpec = tween(durationMillis = 0)
            )
        },
        modifier = modifier
    ) {
        homeScreen(
            navController = navController,
            mainNavController = mainNavController
        )
        searchScreen(
            navController = navController,
            mainNavController = mainNavController
        )
        calendarScreen(
            navController = navController,
            mainNavController = mainNavController
        )
        myScreen(
            navController = navController,
            mainNavController = mainNavController
        )
    }
}

@Composable
private fun MainBottomBar(
    currentDestination: MainDestination,
    onBottomNavigationClick: (destination: MainDestination) -> Unit,
) {
    val bottomNavigationItems = listOf(
        MainDestination.HOME,
        MainDestination.SEARCH,
        MainDestination.CALENDAR,
        MainDestination.MY
    )
    BottomNavigation(
        backgroundColor = Gray900,
    ) {
        bottomNavigationItems.forEach { destination ->
            val selected = currentDestination == destination
            BottomNavigationItem(
                selected = selected,
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = destination.icon),
                        tint = if (selected) White else Gray400,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = destination.text,
                        style = TextStyle(
                            fontFamily = Suit,
                            fontWeight = FontWeight.W700,
                            fontSize = 10.sp,
                            lineHeight = 12.48.sp,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        color = if (selected) White else Gray400,
                    )
                },
                onClick = { onBottomNavigationClick(destination) }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    WonderTheme {
        MainScreen(
            navController = rememberAnimatedNavController(),
            currentDestination = MainDestination.HOME,
            onBottomNavigationClick = {}
        )
    }
}
