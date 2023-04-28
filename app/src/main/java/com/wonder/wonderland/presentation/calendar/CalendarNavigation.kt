package com.wonder.wonderland.presentation.calendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.imaec.feature.festival.navigateToFestival
import com.wonder.component.navigation.Navigate
import com.wonder.wonderland.presentation.MainViewModel
import com.wonder.wonderland.presentation.calendar.vm.CalendarViewModel

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
        val calendarViewModel: CalendarViewModel = hiltViewModel(parentEntry)

        CalendarView(
            mainViewModel = mainViewModel,
            calendarViewModel = calendarViewModel,
            onBackClick = mainNavController::popBackStack,
            onMoveFestival = navController::navigateToFestival
        )
    }
}
