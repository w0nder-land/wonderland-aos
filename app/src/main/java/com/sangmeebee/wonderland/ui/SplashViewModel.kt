package com.sangmeebee.wonderland.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    // splash
    var isReady: Boolean = false
        private set

    init {
        viewModelScope.launch {
            delay(3000)
            isReady = true
        }
    }
}
