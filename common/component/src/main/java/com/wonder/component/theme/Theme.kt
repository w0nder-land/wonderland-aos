package com.wonder.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Wonder500,
    secondary = Gray900,
    error = Red900,
    background = White,
    surface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Wonder500,
    secondary = Gray900,
    error = Red900,
    background = White,
    surface = White,
)

@Composable
fun WonderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}
