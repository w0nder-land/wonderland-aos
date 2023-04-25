package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderEvent

internal sealed interface OnboardingEvent : WonderEvent {

    object ClickKakaoLogin : OnboardingEvent
    data class KakaoLogin(val userId: Long) : OnboardingEvent
    object ClickLookAround : OnboardingEvent
}
