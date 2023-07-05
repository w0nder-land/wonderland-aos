package com.wonder.feature.my

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.feature.my.vm.MyViewModel
import com.wonder.resource.R

@Composable
fun MyView(
    myViewModel: MyViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    BackHandler { onBackClick() }

    MyScreen()
}

@Composable
private fun MyScreen() {
    Scaffold(
        containerColor = Gray900,
        topBar = {},
        content = { padding ->
            MyContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun MyContent(modifier: Modifier) {
    val menuItems = listOf(
        "좋아요한 축제",
        "최근 본 축제",
        "공지사항",
        "문의하기",
    )
    LazyColumn(modifier = modifier) {
        item {
            MyProfileItemView()

            HorizontalDivider(color = Gray600)
        }

        items(menuItems) { menu ->
            MyMenuItemView(menu = menu)
        }

        item {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 32.dp)
                    .singleClick(hasRipple = false) {  },
                text = "로그아웃",
                style = Body2,
                color = Gray200
            )
        }
    }
}

@Composable
private fun MyProfileItemView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 37.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.img_sample_guest_1),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Icon(
                modifier = Modifier
                    .padding(end = 2.dp, bottom = 4.dp)
                    .size(20.dp)
                    .background(
                        color = Gray400,
                        shape = CircleShape
                    )
                    .padding(3.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_camera_fill),
                tint = White,
                contentDescription = null
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "농구하는기린1234",
                style = Subtitle1,
                color = Gray50
            )

            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_pencil_fill),
                tint = Gray300,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun MyMenuItemView(menu: String) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .singleClick {}
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = menu,
            style = Subtitle2,
            color = White
        )

        Icon(
            modifier = Modifier
                .size(28.dp)
                .padding(6.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            tint = White,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun MyScreenPreview() {
    WonderTheme {
        MyScreen()
    }
}
