package com.wonder.wonderland.presentation.calendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.imaec.feature.festival.navigateToFestival
import com.wonder.component.navigation.Navigate

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.calendarScreen(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    composable(route = Navigate.Screen.Calendar.route) {
        CalendarView(
            onBackClick = mainNavController::popBackStack,
            onMoveFestival = navController::navigateToFestival
        )
    }
}
