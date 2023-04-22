package com.wonder.feature.onboarding.vm

import com.wonder.base.WonderViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor() :
    WonderViewModel<OnboardingEvent, OnboardingResult, OnboardingState, OnboardingEffect>(OnboardingState()) {

    override fun Flow<OnboardingEvent>.toResults(): Flow<OnboardingResult> = merge(
        filterIsInstance<OnboardingEvent.ClickKakaoLogin>().toClickKakaoLoginResult(),
        filterIsInstance<OnboardingEvent.ClickLookAround>().toClickLookAroundResult(),
    )

    override fun Flow<OnboardingResult>.toEffects(): Flow<OnboardingEffect> = merge(
        filterIsInstance<OnboardingResult.KakaoLogin>().toClickKakaoLoginEffect(),
        filterIsInstance<OnboardingResult.MoveMain>().toClickLookAroundEffect()
    )

    override fun OnboardingResult.reduce(state: OnboardingState): OnboardingState = state

    private fun Flow<OnboardingEvent.ClickKakaoLogin>.toClickKakaoLoginResult() = map {
        OnboardingResult.KakaoLogin
    }

    private fun Flow<OnboardingEvent.ClickLookAround>.toClickLookAroundResult() = map {
        OnboardingResult.MoveMain
    }

    private fun Flow<OnboardingResult.KakaoLogin>.toClickKakaoLoginEffect() = map {
        OnboardingEffect.KakaoLogin
    }

    private fun Flow<OnboardingResult.MoveMain>.toClickLookAroundEffect() = map {
        OnboardingEffect.MoveMain
    }
}
