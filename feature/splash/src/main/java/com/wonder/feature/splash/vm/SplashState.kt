package com.wonder.feature.splash.vm

import com.wonder.base.WonderState

internal data class SplashState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false
) : WonderState
