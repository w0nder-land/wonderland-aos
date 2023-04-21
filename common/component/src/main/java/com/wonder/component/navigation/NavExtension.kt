package com.wonder.component.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateWithPopBackStack(route: String, navOptions: NavOptions? = null) {
    popBackStack()
    navigate(route, navOptions)
}
