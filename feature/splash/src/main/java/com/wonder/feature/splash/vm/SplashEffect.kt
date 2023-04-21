package com.wonder.feature.splash.vm

import com.wonder.base.WonderEffect

internal sealed interface SplashEffect : WonderEffect {

    object MoveMainScreen : SplashEffect
    object MoveOnboardingScreen : SplashEffect
}
