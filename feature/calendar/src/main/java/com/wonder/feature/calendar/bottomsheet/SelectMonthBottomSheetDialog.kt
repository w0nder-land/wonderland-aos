package com.wonder.feature.calendar.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Suit
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.bottomsheet.BottomSheetTopDot
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.picker.WheelPicker
import com.wonder.component.ui.singleClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectMonthBottomSheetDialog(
    currentMonth: String,
    yearMonthItems: List<String>,
    onSelectYearMonth: (yearMonth: String) -> Unit,
    onBottomSheetClose: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedIndex by remember { mutableStateOf(yearMonthItems.indexOf(currentMonth)) }
    val gradientColors = listOf(
        Gray800.copy(alpha = 1f),
        Gray800.copy(alpha = 0f)
    )

    ModalBottomSheet(
        onDismissRequest = onBottomSheetClose,
        sheetState = sheetState,
        dragHandle = {
            BottomSheetTopDot()
        },
        containerColor = Gray800
    ) {
        Column(modifier = Modifier.height(346.dp)) {
            Box {
                WheelPicker(
                    startIndex = selectedIndex,
                    menuCount = yearMonthItems.size,
                    isInfinite = false,
                    onCurrentIndex = { selectedIndex = it },
                    content = { index ->
                        Text(
                            text = yearMonthItems[index],
                            style = TextStyle(
                                fontFamily = Suit,
                                fontWeight = FontWeight.W500,
                                fontSize = 24.sp,
                                lineHeight = 36.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            ),
                            color = White
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(brush = Brush.verticalGradient(colors = gradientColors))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(
                            brush = Brush.verticalGradient(colors = gradientColors.reversed())
                        )
                        .align(Alignment.BottomCenter)
                )
            }

            HorizontalDivider(color = Gray600)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .singleClick {
                            onSelectYearMonth(yearMonthItems[selectedIndex])
                            onBottomSheetClose()
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "확인",
                        style = Subtitle2,
                        color = Gray800
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectMonthBottomSheetDialogPreview() {
    WonderTheme {
        SelectMonthBottomSheetDialog(
            currentMonth = "2023년 4월",
            yearMonthItems = listOf(
                "2023년 1월",
                "2023년 2월",
                "2023년 3월",
                "2023년 4월",
                "2023년 5월",
                "2023년 6월",
                "2023년 7월",
            ),
            onSelectYearMonth = {},
            onBottomSheetClose = {}
        )
    }
}
