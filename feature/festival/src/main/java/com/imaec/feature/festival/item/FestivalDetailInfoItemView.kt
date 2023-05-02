package com.imaec.feature.festival.item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

private val MAX_HEIGHT = 7000.dp
private val DEFAULT_MIN_HEIGHT = 808.dp

@Composable
internal fun FestivalDetailInfoItemView(
    images: List<String>
) {
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = density.run { screenWidth.toPx() }.toInt()
    val screenHeightPx = density.run { screenHeight.toPx() }.toInt()
    var isExpandedImage by remember { mutableStateOf(false) }
    var minHeight by remember { mutableStateOf(MAX_HEIGHT) }
    var imageHeight by remember { mutableStateOf(0.dp) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "상세정보",
            style = Heading2,
            color = Gray100
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .height(if (isExpandedImage) imageHeight else minHeight)
        ) {
            Column(
                modifier = Modifier.onGloballyPositioned {
                    val height = density.run { it.size.height.toDp() }

                    if (imageHeight == 0.dp && height != MAX_HEIGHT) {
                        imageHeight = height
                    }
                    if (minHeight > height && !isExpandedImage) {
                        minHeight = if (height > DEFAULT_MIN_HEIGHT) {
                            DEFAULT_MIN_HEIGHT
                        } else {
                            height
                        }
                    }
                },
            ) {
                images.forEach { imageUrl ->
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .size(width = screenWidthPx * 10, height = screenHeightPx * 10)
                                .crossfade(true)
                                .build()
                        ),
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.TopCenter,
                        contentDescription = null
                    )
                }
            }

            if (minHeight >= DEFAULT_MIN_HEIGHT &&
                imageHeight > DEFAULT_MIN_HEIGHT &&
                !isExpandedImage
            ) {
                val colors = listOf(
                    Gray900.copy(alpha = 0f),
                    Gray900.copy(alpha = 0.4323f),
                    Gray900.copy(alpha = 1f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(155.dp)
                        .background(
                            brush = Brush.verticalGradient(colors = colors)
                        )
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                            .singleClick { isExpandedImage = true }
                            .align(Alignment.BottomCenter)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            tint = White,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            heightSize = 8.dp,
            color = Gray800
        )
    }
}

@Preview
@Composable
private fun FestivalDetailInfoItemViewPreview() {
    WonderTheme {
        FestivalDetailInfoItemView(
            images = listOf("1", "2")
        )
    }
}
