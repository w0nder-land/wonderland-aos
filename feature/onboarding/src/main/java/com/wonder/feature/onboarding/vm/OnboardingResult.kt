package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderResult

internal sealed interface OnboardingResult : WonderResult {

    object ClickKakaoLogin : OnboardingResult
    object MoveMain : OnboardingResult
}
