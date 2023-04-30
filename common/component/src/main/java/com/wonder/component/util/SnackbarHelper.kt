package com.wonder.component.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

private val _snackbarState = MutableSharedFlow<String>()
val snackbarState = _snackbarState.asSharedFlow()

suspend fun showMessage(message: String) {
    _snackbarState.emit(message)
}
