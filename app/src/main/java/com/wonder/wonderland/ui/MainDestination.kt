package com.wonder.wonderland.ui

import androidx.annotation.DrawableRes
import com.wonder.component.navigate.calendarRoute
import com.wonder.component.navigate.homeRoute
import com.wonder.component.navigate.myRoute
import com.wonder.component.navigate.searchRoute
import com.wonder.resource.R

enum class MainDestination(
    @DrawableRes val icon: Int,
    val text: String,
    val route: String
) {
    HOME(
        icon = R.drawable.ic_bottom_home,
        text = "홈",
        route = homeRoute
    ),
    SEARCH(
        icon = R.drawable.ic_bottom_search,
        text = "검색",
        route = searchRoute
    ),
    CALENDAR(
        icon = R.drawable.ic_bottom_calendar,
        text = "캘린더",
        route = calendarRoute
    ),
    MY(
        icon = R.drawable.ic_bottom_my,
        text = "마이",
        route = myRoute
    )
}
