package com.wonder.wonderland.ui.calendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.composable
import com.wonder.component.navigate.calendarRoute
import com.wonder.component.navigate.mainRoute
import com.wonder.wonderland.ui.MainViewModel

fun NavController.navigateToCalendar() {
    navigate(
        route = calendarRoute,
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
    composable(route = calendarRoute) { entry ->
        val parentEntry: NavBackStackEntry = remember(entry) {
            navController.getBackStackEntry(mainRoute)
        }
        val mainViewModel: MainViewModel = hiltViewModel(parentEntry)

        CalendarView(
            mainViewModel = mainViewModel,
            onBackClick = { mainNavController.popBackStack() }
        )
    }
}
