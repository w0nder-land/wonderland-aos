package com.wonder.feature.splash.vm

import com.wonder.base.WonderResult

internal sealed interface SplashResult : WonderResult {

    object SuccessCheckServer : SplashResult
}
