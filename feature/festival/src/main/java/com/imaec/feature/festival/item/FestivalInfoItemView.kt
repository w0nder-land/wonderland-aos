package com.imaec.feature.festival.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.domain.model.festival.FestivalDetailLink
import com.wonder.domain.model.festival.FestivalDetailTicketing
import com.wonder.resource.R

@Composable
internal fun FestivalInfoItemView(
    startDate: String,
    endDate: String,
    location: String?,
    runningTime: Int?,
    age: String,
    links: List<FestivalDetailLink>,
    ticketingDate: String?,
    ticketingItems: List<FestivalDetailTicketing>
) {
    val infoItems = mutableListOf<FestivalInfo>()
    infoItems.add(
        FestivalInfo(
            title = "기간",
            content = if (startDate == endDate) startDate else "$startDate ~ $endDate"
        )
    )
    if (location != null) {
        infoItems.add(
            FestivalInfo(
                title = "장소",
                content = location
            )
        )
    }
    if (runningTime != null) {
        infoItems.add(
            FestivalInfo(
                title = "관람시간",
                content = "${runningTime}분"
            )
        )
    }
    infoItems.add(
        FestivalInfo(
            title = "관람연령",
            content = age
        )
    )
    if (links.isNotEmpty()) {
        infoItems.add(
            FestivalInfo(
                title = "공식",
                content = links.joinToString(" | ") { it.linkType }
            )
        )
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "기본 정보",
            style = Heading2,
            color = Gray100
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            infoItems.forEach { festivalInfo ->
                Row(
                    modifier = Modifier.height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.width(80.dp),
                        text = festivalInfo.title,
                        style = Subtitle2,
                        color = Gray100
                    )

                    Text(
                        text = festivalInfo.content,
                        style = Body1,
                        color = Gray200
                    )
                }
            }
        }

        FestivalInfoTicketView(
            ticketingDate = ticketingDate,
            ticketingItems = ticketingItems
        )
    }
}

@Composable
private fun FestivalInfoTicketView(
    ticketingDate: String?,
    ticketingItems: List<FestivalDetailTicketing>,
) {
    ticketingDate ?: return

    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .background(
                color = Gray800,
                shape = RoundedCornerShape(3.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "티켓 오픈",
                style = Subtitle2,
                color = Gray100
            )

            Text(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .border(
                        width = 1.dp,
                        color = Wonder500,
                        shape = CircleShape
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                text = "D-24",
                style = Caption1,
                color = Wonder500
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = ticketingDate,
                style = Body2,
                color = Gray100
            )
        }

        if (ticketingItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ticketingItems.forEachIndexed { index, festivalDetailTicketing ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = festivalDetailTicketing.title,
                                style = Body2,
                                color = Gray200
                            )

                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_arrow_uprignt),
                                tint = Gray200,
                                contentDescription = null
                            )
                        }

                        if (index < ticketingItems.lastIndex) {
                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(13.dp)
                                    .background(Gray600)
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 60.dp)
                .padding(horizontal = 20.dp),
            color = Gray600
        )
    }
}

@Preview
@Composable
private fun FestivalInfoItemViewPreview() {
    WonderTheme {
        FestivalInfoItemView(
            startDate = "2023.04.23(일)",
            endDate = "2023.04.26(수)",
            location = "일산 킨텍스 제2전시관 7,8홀",
            runningTime = 100,
            age = "전체관람가",
            links = emptyList(),
            ticketingDate = "2023.03.20(목)",
            ticketingItems = emptyList()
        )
    }
}
