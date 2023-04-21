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
    WonderViewModel<OnboardingEvent, OnboardingState, OnboardingEffect>(OnboardingState()) {

    override fun Flow<OnboardingEvent>.toEffects(): Flow<OnboardingEffect> = merge(
        filterIsInstance<OnboardingEvent.ClickKakaoLogin>().toClickKakaoLoginEffect(),
        filterIsInstance<OnboardingEvent.ClickLookAround>().toClickLookAroundEffect()
    )

    override fun OnboardingEvent.reduce(state: OnboardingState): OnboardingState = state

    private fun Flow<OnboardingEvent.ClickKakaoLogin>.toClickKakaoLoginEffect() = map {
        OnboardingEffect.KakaoLogin
    }

    private fun Flow<OnboardingEvent.ClickLookAround>.toClickLookAroundEffect() = map {
        OnboardingEffect.MoveMain
    }
}
