package com.wonder.feature.splash.vm

import com.wonder.base.WonderViewModel
import com.wonder.domain.usecase.CheckServerUseCase
import com.wonder.domain.usecase.auth.IsLoginUseCase
import com.wonder.domain.usecase.successOr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val checkServerUseCase: CheckServerUseCase,
    private val isLoginUseCase: IsLoginUseCase,
) : WonderViewModel<SplashEvent, SplashResult, SplashState, SplashEffect>(SplashState()) {

    override fun Flow<SplashEvent>.toResults(): Flow<SplashResult> = merge(
        filterIsInstance<SplashEvent.CheckServer>().toCheckServerResult()
    )

    override fun Flow<SplashResult>.toEffects(): Flow<SplashEffect> = merge(
        filterIsInstance<SplashResult.SuccessCheckServer>().toCheckServerEffect()
    )

    override fun SplashResult.reduce(state: SplashState): SplashState = state

    private fun Flow<SplashEvent.CheckServer>.toCheckServerResult(): Flow<SplashResult> =
        mapLatest {
            checkServerUseCase()
            val isLogin = isLoginUseCase().successOr(false)
            SplashResult.SuccessCheckServer(isLogin)
        }

    private fun Flow<SplashResult.SuccessCheckServer>.toCheckServerEffect(): Flow<SplashEffect> =
        mapLatest {
            if (it.isLogin) SplashEffect.MoveMainScreen else SplashEffect.MoveOnboardingScreen
        }
}
