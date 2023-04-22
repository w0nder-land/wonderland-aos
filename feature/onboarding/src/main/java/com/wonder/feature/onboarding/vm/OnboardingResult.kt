package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderResult

internal sealed interface OnboardingResult : WonderResult {

    object KakaoLogin : OnboardingResult
    object MoveMain : OnboardingResult
}
