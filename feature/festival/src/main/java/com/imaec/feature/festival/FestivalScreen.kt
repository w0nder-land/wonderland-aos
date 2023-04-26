package com.imaec.feature.festival

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imaec.feature.festival.vm.FestivalViewModel
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
internal fun FestivalView(
    festivalViewModel: FestivalViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    FestivalScreen(
        onBackClick = onBack
    )
}

@Composable
private fun FestivalScreen(
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            FestivalTopBar(
                onBackClick = onBackClick
            )
        },
        content = { padding ->
            FestivalContent(
                modifier = Modifier.padding(padding)
            )
        },
        bottomBar = {
            FestivalBottomBar()
        }
    )
}

@Composable
private fun FestivalTopBar(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .singleClick(shape = CircleShape) { onBackClick() }
                .padding(6.dp),
            painter = painterResource(id = R.drawable.ic_back),
            tint = White,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .size(36.dp)
                .singleClick(shape = CircleShape) { }
                .padding(6.dp),
            painter = painterResource(id = R.drawable.ic_share),
            tint = White,
            contentDescription = null
        )
    }
}

@Composable
private fun FestivalContent(
    modifier: Modifier
) {
}

@Composable
private fun FestivalBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.ic_heart_fill),
                    tint = Wonder500,
                    contentDescription = null
                )

                Text(
                    text = "13",
                    style = Caption1,
                    color = White
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(
                        color = Gray700,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .singleClick(shape = RoundedCornerShape(6.dp)) { }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "예매링크가 없어요",
                    style = Subtitle2,
                    color = Gray500
                )
            }
        }

        HorizontalDivider(color = Gray600)
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalScreenPreview() {
    WonderTheme {
        FestivalScreen(
            onBackClick = {}
        )
    }
}
