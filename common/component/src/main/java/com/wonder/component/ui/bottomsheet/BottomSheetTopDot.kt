package com.wonder.component.ui.bottomsheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray800

@Composable
fun BottomSheetTopDot() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(Gray800)
    ) {
        val width = size.width
        val height = size.height

        drawLine(
            start = Offset(x = width / 2 - 18.dp.toPx(), y = height / 2),
            end = Offset(x = width / 2 + 18.dp.toPx(), y = height / 2),
            color = Gray500,
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}
