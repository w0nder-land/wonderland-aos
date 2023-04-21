package com.wonder.feature.splash.vm

import com.wonder.base.WonderViewModel
import com.wonder.domain.usecase.CheckServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val checkServerUseCase: CheckServerUseCase
) : WonderViewModel<SplashEvent, SplashState, SplashEffect>(SplashState()) {

    override fun Flow<SplashEvent>.toEffects(): Flow<SplashEffect> = merge(
        filterIsInstance<SplashEvent.CheckServer>().toCheckServerEffect()
    )

    override fun SplashEvent.reduce(state: SplashState): SplashState = state

    private fun Flow<SplashEvent.CheckServer>.toCheckServerEffect(): Flow<SplashEffect> =
        mapLatest {
            checkServerUseCase()
            SplashEffect.MoveOnboardingScreen
        }
}
