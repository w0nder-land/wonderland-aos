package com.wonder.component.ui.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray100

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    heightSize: Dp = 1.dp,
    color: Color = Gray100,
) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .height(heightSize),
        color = color
    )
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    widthSize: Dp = 1.dp,
    color: Color = Gray100,
) {
    Divider(
        modifier = modifier
            .fillMaxHeight()
            .width(widthSize),
        color = color
    )
}

@Composable
fun HorizontalGradientDivider(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color.Black.copy(alpha = 0f),
        Color.Black.copy(alpha = 0.04f)
    )
    Spacer(
        modifier = modifier
            .height(6.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(colors = colors)
            )
    )
}
