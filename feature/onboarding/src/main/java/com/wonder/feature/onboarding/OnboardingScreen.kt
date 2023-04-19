package com.wonder.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.resource.R

@Composable
fun OnboardingView() {
    BackHandler(enabled = false) {
    }

    OnboardingScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingScreen() {
    val pagerState = rememberPagerState()
    val onboardingImages = listOf(
        R.drawable.img_onboarding_card_1,
        R.drawable.img_onboarding_card_1,
        R.drawable.img_onboarding_card_3,
        R.drawable.img_onboarding_card_start
    )

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        pageCount = onboardingImages.size
    ) { page ->
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(onboardingImages[page]),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            OnboardingIndicatorView(
                pagerState = pagerState,
                count = onboardingImages.size
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingIndicatorView(
    pagerState: PagerState,
    count: Int,
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(count) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == pagerState.currentPage) {
                            Wonder500.copy(alpha = 0.4f)
                        } else {
                            Gray300.copy(alpha = 0.4f)
                        }
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    WonderTheme {
        OnboardingScreen()
    }
}
