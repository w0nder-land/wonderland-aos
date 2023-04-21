package com.wonder.wonderland.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wonder.domain.usecase.CheckServerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkServerUseCase: CheckServerUseCase
) : ViewModel() {

    private val _currentNavigation = MutableStateFlow(MainDestination.HOME)
    val currentNavigation = _currentNavigation.asStateFlow()

    init {
        viewModelScope.launch {
            checkServerUseCase()
        }
    }

    fun selectBottomNavigationItem(destination: MainDestination) {
        _currentNavigation.value = destination
    }
}
