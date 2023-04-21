package com.wonder.component.navigation

sealed class Navigate(val route: String) {
    sealed class Screen {
        object Splash : Navigate("splash")
        object Onboarding : Navigate("onboarding")
        object Main : Navigate("main")
        object Home : Navigate("home")
        object Search : Navigate("search")
        object Calendar : Navigate("calendar")
        object My : Navigate("my")
    }
}
