package com.wonder.wonderland.presentation.calendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigation.Navigate
import com.wonder.wonderland.presentation.MainViewModel

fun NavController.navigateToCalendar() {
    navigate(
        route = Navigate.Screen.Calendar.route,
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

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.calendarScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.Calendar.route) { entry ->
        val parentEntry: NavBackStackEntry = remember(entry) {
            navController.getBackStackEntry(Navigate.Screen.Main.route)
        }
        val mainViewModel: MainViewModel = hiltViewModel(parentEntry)

        CalendarView(
            mainViewModel = mainViewModel,
            onBackClick = { mainNavController.popBackStack() }
        )
    }
}
