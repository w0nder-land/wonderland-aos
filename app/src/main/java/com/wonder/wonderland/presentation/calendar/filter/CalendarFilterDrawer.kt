package com.wonder.wonderland.presentation.calendar.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
internal fun CalendarFilterDrawer() {
    val categories = listOf(
        CalendarFilter(
            title = "전체",
            count = 457
        ),
        CalendarFilter(
            title = "뮤직 페스티벌",
            count = 345,
            isSelected = true
        ),
        CalendarFilter(
            title = "인디/록",
            count = 56
        ),
        CalendarFilter(
            title = "내한 공연",
            count = 28
        ),
        CalendarFilter(
            title = "힙합/EDM",
            count = 28
        ),
    )
    val stateItems = listOf(
        CalendarFilter(
            title = "전체",
            count = 457
        ),
        CalendarFilter(
            title = "진행 중인 축제",
            count = 345,
            isSelected = true
        ),
        CalendarFilter(
            title = "시작 예정 축제",
            count = 56,
            isSelected = true
        ),
        CalendarFilter(
            title = "종료된 축제",
            count = 28
        ),
    )
    val regions = listOf(
        CalendarFilter(
            title = "전체",
            count = 457,
            isSelected = true
        ),
        CalendarFilter(
            title = "서울",
            count = 4
        ),
        CalendarFilter(
            title = "대학로",
            count = 4
        ),
        CalendarFilter(
            title = "홍대",
            count = 3
        ),
        CalendarFilter(
            title = "경기",
            count = 2
        ),
        CalendarFilter(
            title = "인천",
            count = 4
        ),
        CalendarFilter(
            title = "대전",
            count = 0
        ),
        CalendarFilter(
            title = "대구",
            count = 2
        ),
    )
    val ages = listOf(
        CalendarFilter(
            title = "전체",
            count = 457
        ),
        CalendarFilter(
            title = "전체 관람가",
            count = 345,
            isSelected = true
        ),
        CalendarFilter(
            title = "만 12세 이상",
            count = 56
        ),
        CalendarFilter(
            title = "만 19세 이상",
            count = 28
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(275.dp)
            .background(Gray800)
    ) {
        CalendarFilterTopView()

        LazyColumn {
            item {
                CalendarFilterItem(title = "장르") {
                    Column(modifier = Modifier.padding(vertical = 14.dp)) {
                        categories.forEach { category ->
                            CalendarFilterItemView(
                                title = category.title,
                                count = category.count,
                                isSelected = category.isSelected
                            )
                        }
                    }
                }
            }

            item {
                CalendarFilterItem(title = "진행상태") {
                    Column(modifier = Modifier.padding(vertical = 14.dp)) {
                        stateItems.forEach { state ->
                            CalendarFilterItemView(
                                title = state.title,
                                count = state.count,
                                isSelected = state.isSelected
                            )
                        }
                    }
                }
            }

            item {
                CalendarFilterItem(title = "지역") {
                    Column(modifier = Modifier.padding(vertical = 14.dp)) {
                        regions.chunked(2).forEach { chunkedRegions ->
                            Row {
                                chunkedRegions.forEach { region ->
                                    CalendarFilterItemView(
                                        title = region.title,
                                        count = region.count,
                                        isSelected = region.isSelected,
                                        width = 137.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                CalendarFilterItem(title = "연령") {
                    Column(modifier = Modifier.padding(vertical = 14.dp)) {
                        ages.forEach { age ->
                            CalendarFilterItemView(
                                title = age.title,
                                count = age.count,
                                isSelected = age.isSelected
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarFilterTopView() {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "필터",
            style = Heading2,
            color = White
        )

        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "27397건",
            style = Body2,
            color = Gray200
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .width(64.dp)
                .height(26.dp)
                .border(
                    width = 1.dp,
                    color = Gray600,
                    shape = RoundedCornerShape(4.dp)
                )
                .singleClick {},
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_retry),
                tint = Gray300,
                contentDescription = null
            )

            Text(
                text = "초기화",
                style = Caption1,
                color = White
            )
        }

        Icon(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(32.dp),
            painter = painterResource(id = R.drawable.ic_close_drawer),
            tint = White,
            contentDescription = null
        )
    }

    HorizontalDivider(color = Gray600)
}

@Composable
private fun CalendarFilterItem(
    title: String,
    content: @Composable () -> Unit,
) {
    var isExpandedItem by remember { mutableStateOf(false) }
    val iconRotate by animateFloatAsState(
        targetValue = if (isExpandedItem) 180f else 0f
    )
    Column(modifier = Modifier.animateContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    color = if (isExpandedItem) Gray700 else Gray800
                )
                .singleClick { isExpandedItem = !isExpandedItem }
                .padding(start = 20.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Subtitle2,
                color = White
            )

            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(iconRotate),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                tint = Gray500,
                contentDescription = null
            )
        }

        if (isExpandedItem) {
            HorizontalDivider(color = Gray600)

            content()
        }
    }

    HorizontalDivider(color = Gray600)
}

@Preview
@Composable
private fun CalendarFilterDrawerPreview() {
    WonderTheme {
        CalendarFilterDrawer()
    }
}
