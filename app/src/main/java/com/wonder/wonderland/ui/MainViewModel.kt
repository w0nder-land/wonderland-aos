package com.wonder.wonderland.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonder.domain.usecase.CheckServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkServerUseCase: CheckServerUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            checkServerUseCase()
        }
    }
}
