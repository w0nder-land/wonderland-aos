package com.wonder.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingView(
    onMoveMain: () -> Unit
) {
    BackHandler(enabled = false) {
    }

    OnboardingScreen(
        onMoveMain = onMoveMain
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreen(
    onMoveMain: () -> Unit
) {
    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp.dp
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val onboardingImages = listOf(
        R.drawable.img_onboarding_card_1,
        R.drawable.img_onboarding_card_2,
        R.drawable.img_onboarding_card_3,
        R.drawable.img_onboarding_card_start
    )

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.bg_wonderland),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Column {
            HorizontalPager(
                modifier = Modifier
                    .padding(top = 116.dp)
                    .height(450.dp),
                state = pagerState,
                pageCount = onboardingImages.size,
                contentPadding = PaddingValues(),
                pageSpacing = -(screenWidthDp - 257.22.dp),
            ) { page ->
                var xOffset = (-35.22).dp
                var yOffset = 0.dp
                var scale = 1f
                if (pagerState.currentPage == 3 && page == 3) {
                    val pageOffset = ((1 + pagerState.currentPageOffsetFraction) - 0.5f) * 2
                    xOffset = (35.22.dp * pageOffset) + (-35.22).dp
                    yOffset = (-60).dp * pageOffset
                    scale = 1f + (pageOffset * 0.34f)
                }

                Image(
                    modifier = Modifier
                        .padding(
                            top = if (page % 2 == 0) 0.dp else 140.dp,
                            bottom = if (page % 2 == 0) 140.dp else 0.dp,
                        )
                        .offset(x = xOffset, y = yOffset)
                        .size(width = 225.dp, height = 310.dp)
                        .scale(scale)
                        .align(Alignment.Start)
                        .blur(
                            radius = if (page == pagerState.currentPage) 0.dp else 4.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                        ),
                    painter = painterResource(onboardingImages[page]),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (pagerState.currentPage < onboardingImages.lastIndex) {
                OnboardingIndicatorView(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    currentPage = pagerState.currentPage,
                    count = onboardingImages.size - 1
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 40.dp, bottom = 39.dp)
                        .fillMaxWidth()
                        .height(58.dp)
                        .border(
                            width = 1.dp,
                            color = Gray800,
                            shape = CircleShape
                        )
                        .singleClick(shape = CircleShape) {
                            scope.launch {
                                pagerState.animateScrollToPage(onboardingImages.lastIndex)
                            }
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "시작하기",
                        style = Subtitle1,
                        color = Gray800
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = 36.dp)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(id = R.drawable.ic_arrow_start),
                        contentDescription = null
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(58.dp)
                        .border(
                            width = 1.dp,
                            color = Gray800,
                            shape = CircleShape
                        ).singleClick(shape = CircleShape) {
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "카카오로 가입하기",
                        style = Subtitle1,
                        color = Gray800
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 28.dp, bottom = 38.dp)
                        .fillMaxWidth()
                        .singleClick(hasRipple = false) { onMoveMain() },
                    text = "간단히 둘러볼래요",
                    style = Body1,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun OnboardingIndicatorView(
    modifier: Modifier,
    currentPage: Int,
    count: Int,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        items(count) { index ->
            Box(
                modifier = Modifier
                    .size(11.dp)
                    .clip(CircleShape)
                    .background(if (index == currentPage) Gray500 else Gray200)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    WonderTheme {
        OnboardingScreen(
            onMoveMain = {}
        )
    }
}
