package com.sangmeebee.wonderland.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import com.sangmeebee.wonderland.feature.onboarding.model.OnBoardingProgress
import com.sangmeebee.wonderland.feature.onboarding.model.OnBoardingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        listOf(
            OnBoardingUiState(OnBoardingProgress.PROGRESS_1, isBlur = false),
            OnBoardingUiState(OnBoardingProgress.PROGRESS_2, isBlur = true),
            OnBoardingUiState(OnBoardingProgress.PROGRESS_3, isBlur = true),
            OnBoardingUiState(OnBoardingProgress.PROGRESS_FINISH, isBlur = true)
        )
    )
    val uiState: StateFlow<List<OnBoardingUiState>> = _uiState.asStateFlow()

    fun fetchBlur(blurIndex: Int) {
        _uiState.value = buildList {
            addAll(uiState.value)
            this.forEachIndexed { index, _ ->
                if (index == blurIndex) {
                    this[index] = this[index].copy(isBlur = false)
                    return@forEachIndexed
                }
                this[index] = this[index].copy(isBlur = true)
            }
        }
    }
}
