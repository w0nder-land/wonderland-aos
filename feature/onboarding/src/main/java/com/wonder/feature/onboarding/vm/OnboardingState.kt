package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderState

internal data class OnboardingState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false
) : WonderState
