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
import com.wonder.resource.R

@Composable
internal fun FestivalInfoItemView() {
    val infoItems = listOf(
        FestivalInfo(
            title = "기간",
            content = "2022.08.06(토) ~2022.08.07(일)"
        ),
        FestivalInfo(
            title = "장소",
            content = "일산 킨텍스 제2전시관 7,8홀"
        ),
        FestivalInfo(
            title = "관람시간",
            content = "600분(인터미션 245분)"
        ),
        FestivalInfo(
            title = "관람연령",
            content = "전체관람가"
        ),
        FestivalInfo(
            title = "공식",
            content = "홈페이지 | 인스타그램 | 유튜브"
        ),
    )
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

        FestivalInfoTicketView()
    }
}

@Composable
private fun FestivalInfoTicketView() {
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
                text = "2023.03.17(금) 20:00",
                style = Body2,
                color = Gray100
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "멜론 티켓",
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

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(13.dp)
                    .background(Gray600)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "인터파크 티켓",
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

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(13.dp)
                    .background(Gray600)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "네이버 예약",
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
        FestivalInfoItemView()
    }
}
