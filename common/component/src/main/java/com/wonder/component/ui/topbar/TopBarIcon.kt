package com.wonder.component.ui.topbar

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.wonder.component.theme.White

data class TopBarIcon(
    @DrawableRes val iconRes: Int,
    val iconTint: Color = White,
    val onIconClick: () -> Unit = {}
)
