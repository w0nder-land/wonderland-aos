package com.wonder.wonderland.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    _networkErrorEvent: MutableSharedFlow<String>
) : ViewModel() {

    val networkErrorEvent = _networkErrorEvent.asSharedFlow()

    private val _currentNavigation = MutableStateFlow(MainDestination.HOME)
    val currentNavigation = _currentNavigation.asStateFlow()

    fun selectBottomNavigationItem(destination: MainDestination) {
        _currentNavigation.value = destination
    }
}
