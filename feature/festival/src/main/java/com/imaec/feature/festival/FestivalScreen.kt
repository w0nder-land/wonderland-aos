package com.imaec.feature.festival

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.imaec.feature.festival.item.FestivalCastingItemView
import com.imaec.feature.festival.item.FestivalDetailInfoItemView
import com.imaec.feature.festival.item.FestivalInfoItemView
import com.imaec.feature.festival.item.FestivalTitleItemView
import com.imaec.feature.festival.vm.FestivalEvent
import com.imaec.feature.festival.vm.FestivalState
import com.imaec.feature.festival.vm.FestivalViewModel
import com.imaec.model.festival.FestivalDetailVo
import com.imaec.model.festival.FestivalStateType
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.topbar.CollapsingTopBar
import com.wonder.component.ui.topbar.TopBar
import com.wonder.component.ui.topbar.TopBarIcon
import com.wonder.component.ui.topbar.platCollapsedScrollBehavior
import com.wonder.component.util.LaunchApp
import com.wonder.resource.R

@Composable
internal fun FestivalView(
    festivalViewModel: FestivalViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    LaunchedEffect(Unit) {
        festivalViewModel.processEvent(FestivalEvent.GetFestival)
    }

    FestivalScreen(
        festivalState = festivalViewModel.states.collectAsStateWithLifecycle().value,
        onBackClick = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FestivalScreen(
    festivalState: FestivalState,
    onBackClick: () -> Unit,
) {
    val scrollBehavior = platCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!festivalState.isLoading && festivalState.festival != null) {
                CollapsingTopBar(
                    scrollBehavior = scrollBehavior,
                    topBarColor = Gray900,
                    topBar = {
                        TopBar(
                            rightIcon = TopBarIcon(
                                iconRes = R.drawable.ic_share,
                                onIconClick = {}
                            ),
                            onLeftIconClick = onBackClick
                        )
                    },
                    content = {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(Gray900),
                            painter = rememberAsyncImagePainter(
                                model = festivalState.festival.thumbNailUrl,
                                error = rememberAsyncImagePainter(
                                    model = festivalState.festival.images.firstOrNull()
                                )
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                )
            }
            TopBar(
                rightIcon = TopBarIcon(
                    iconRes = R.drawable.ic_share,
                    onIconClick = {}
                ),
                onLeftIconClick = onBackClick
            )
        },
        content = { padding ->
            if (!festivalState.isLoading && festivalState.festival != null) {
                FestivalContent(
                    modifier = Modifier.padding(padding),
                    festival = festivalState.festival
                )
            }
        },
        bottomBar = {
            if (!festivalState.isLoading && festivalState.festival != null) {
                FestivalBottomBar(
                    festival = festivalState.festival
                )
            }
        }
    )
}

@Composable
private fun FestivalContent(
    modifier: Modifier,
    festival: FestivalDetailVo,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 32.dp, bottom = 85.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        item {
            FestivalTitleItemView(
                festivalName = festival.festivalName,
                festivalDescription = festival.description
            )
        }

        item {
            FestivalInfoItemView(
                startDate = festival.startDate,
                endDate = festival.endDate,
                location = festival.location,
                runningTime = festival.runningTime,
                age = festival.age,
                links = festival.links,
                dDay = festival.dDay,
                ticketingDate = festival.ticketingDate,
                ticketingItems = festival.ticketingItems
            )
        }

        if (festival.castings.isNotEmpty()) {
            item {
                FestivalCastingItemView(
                    castings = festival.castings
                )
            }
        }

        if (festival.images.isNotEmpty()) {
            item {
                FestivalDetailInfoItemView(
                    images = festival.images
                )
            }
        }

        // TODO : 추후 추가 예정
//        item {
//            FestivalRecommendItemView()
//        }
    }
}

@Composable
private fun FestivalBottomBar(
    festival: FestivalDetailVo,
) {
    val context = LocalContext.current
    val buttonText by rememberUpdatedState(
        newValue = if (festival.progressState == FestivalStateType.N) {
            "종료된 공연입니다."
        } else if (festival.bookingLinkUrl != null) {
            "예매하기"
        } else {
            "예매링크가 없어요"
        }
    )
    val isButtonEnabled by rememberUpdatedState(
        newValue = festival.progressState != FestivalStateType.N && festival.bookingLinkUrl != null
    )

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
                    painter = painterResource(
                        id = if (festival.isLiked) {
                            R.drawable.ic_heart_fill
                        } else {
                            R.drawable.ic_heart
                        }
                    ),
                    tint = if (festival.isLiked) Wonder500 else White,
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
                        color = if (isButtonEnabled) White else Gray700,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .singleClick(
                        shape = RoundedCornerShape(6.dp),
                        enabled = isButtonEnabled
                    ) {
                        LaunchApp.launchBrowser(
                            context = context,
                            url = festival.bookingLinkUrl ?: return@singleClick
                        )
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = buttonText,
                    style = Subtitle2,
                    color = if (isButtonEnabled) Gray900 else Gray500
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
            festivalState = FestivalState(),
            onBackClick = {}
        )
    }
}
