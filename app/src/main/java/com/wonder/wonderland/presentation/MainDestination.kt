package com.wonder.wonderland.presentation

import androidx.annotation.DrawableRes
import com.wonder.component.navigation.Navigate
import com.wonder.resource.R

enum class MainDestination(
    @DrawableRes val icon: Int,
    val text: String,
    val route: String
) {
    HOME(
        icon = R.drawable.ic_bottom_home,
        text = "홈",
        route = Navigate.Screen.Home.route
    ),
    SEARCH(
        icon = R.drawable.ic_bottom_search,
        text = "검색",
        route = Navigate.Screen.Search.route
    ),
    CALENDAR(
        icon = R.drawable.ic_bottom_calendar,
        text = "캘린더",
        route = Navigate.Screen.Calendar.route
    ),
    MY(
        icon = R.drawable.ic_bottom_my,
        text = "마이",
        route = Navigate.Screen.My.route
    )
}
