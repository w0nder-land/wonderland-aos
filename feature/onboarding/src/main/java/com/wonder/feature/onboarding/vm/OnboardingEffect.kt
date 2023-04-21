package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderEffect

internal sealed interface OnboardingEffect : WonderEffect {

    object KakaoLogin : OnboardingEffect
    object MoveMain : OnboardingEffect
}
