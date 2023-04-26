package com.wonder.feature.splash.vm

import com.wonder.base.WonderResult

internal sealed interface SplashResult : WonderResult {

    data class SuccessCheckServer(val isLogin: Boolean) : SplashResult
}
