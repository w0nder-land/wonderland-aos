package com.wonder.component.ui.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Suit
import com.wonder.component.theme.White
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun WheelPicker(
    startIndex: Int,
    menuCount: Int,
    onCurrentIndex: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    itemHeight: Dp = 36.dp,
    maxVisibleMenuCount: Int = 7,
    selectorBoxShape: Shape = RoundedCornerShape(6.dp),
    selectorBoxColor: Color? = Gray600,
    isInfinite: Boolean = true,
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    val centerItemIndex =
        if (isInfinite) startIndex - (maxVisibleMenuCount / 2) else kotlin.math.abs(startIndex - (maxVisibleMenuCount / 2))
    val maxIntQuotient = (1000000000 / menuCount) * menuCount
    val infiniteIndex = remember { maxIntQuotient + centerItemIndex }
    val itemHeightToPx = with(LocalDensity.current) { itemHeight.toPx() }
    val lazyListState: LazyListState =
        rememberLazyListState(if (isInfinite) infiniteIndex else centerItemIndex)
    val snappedIndex =
        remember { mutableStateOf(if (isInfinite) infiniteIndex else centerItemIndex) }
    val firstVisibleIndex = remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset =
        remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }
    val flingBehavior = rememberSnapperFlingBehavior(lazyListState = lazyListState)

    LaunchedEffect(firstVisibleItemScrollOffset.value, firstVisibleIndex.value) {
        snappedIndex.value =
            if (firstVisibleIndex.value == 0) {
                lazyListState.firstVisibleItemScrollOffset / itemHeightToPx.toInt()
            } else {
                firstVisibleIndex.value + (maxVisibleMenuCount / 2)
            }
        onCurrentIndex(snappedIndex.value % menuCount)
    }

    Box(
        modifier = modifier.background(Gray800),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .height(itemHeight)
                .background(
                    color = selectorBoxColor ?: White,
                    shape = selectorBoxShape
                )
        )
        LazyColumn(
            modifier = Modifier
                .height(itemHeight * maxVisibleMenuCount)
                .align(Alignment.Center),
            state = lazyListState,
            flingBehavior = flingBehavior
        ) {
            items(count = if (isInfinite) Int.MAX_VALUE else menuCount) { index ->
                Layout(
                    content = {
                        Box(
                            modifier = Modifier
                                .height(itemHeight)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            content(index % menuCount)
                        }
                    },
                    measurePolicy = { measurables, constraints ->
                        val placeable = measurables.first().measure(constraints)
                        val maxHeightInPx = (itemHeight * maxVisibleMenuCount).roundToPx()
                        // 첫 번째 아이템 position
                        val startSpace: Float =
                            if (isInfinite) {
                                0f
                            } else {
                                when (index) {
                                    0 -> itemHeightToPx.toInt() * ((maxVisibleMenuCount - 1) / 2f)
                                    else -> 0f
                                }
                            }
                        val endSpace =
                            if (index == menuCount - 1) {
                                (maxHeightInPx - itemHeightToPx.toInt()) / 2
                            } else {
                                0
                            }
                        val height = startSpace + placeable.height + endSpace
                        layout(placeable.width, height.toInt()) {
                            val y = when (index) {
                                0 -> startSpace.toInt()
                                else -> 0
                            }
                            placeable.place(0, y)
                        }
                    }
                )
            }
        }
        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(itemHeight * (maxVisibleMenuCount / 2))
        ) {
            drawRoundRect(
                color = Gray800,
                alpha = 0.6f
            )
        }
        Canvas(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(itemHeight * (maxVisibleMenuCount / 2))
        ) {
            drawRoundRect(
                color = Gray800,
                alpha = 0.6f
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WheelPickerPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    val pickerItems = listOf(
        "2022년 10월",
        "2022년 11월",
        "2022년 12월",
        "2023년 1월",
        "2023년 2월",
        "2023년 3월",
        "2023년 4월"
    )
    WheelPicker(
        menuCount = pickerItems.size,
        itemHeight = 36.dp,
        startIndex = 3,
        maxVisibleMenuCount = 7,
        isInfinite = false,
        onCurrentIndex = { currentIndex ->
            selectedIndex = currentIndex
        },
        content = { index ->
            Text(
                text = pickerItems[index],
                style = TextStyle(
                    fontFamily = Suit,
                    fontWeight = FontWeight.W500,
                    fontSize = if (selectedIndex == index) 24.sp else 20.sp,
                    lineHeight = 36.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = White
            )
        }
    )
}
