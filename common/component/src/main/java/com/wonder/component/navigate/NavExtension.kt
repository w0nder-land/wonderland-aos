package com.wonder.component.navigate

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateWithPopBackStack(route: String, navOptions: NavOptions? = null) {
    popBackStack()
    navigate(route, navOptions)
}
