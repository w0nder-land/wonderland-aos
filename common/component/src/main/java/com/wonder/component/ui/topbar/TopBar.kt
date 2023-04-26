package com.wonder.component.ui.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.White
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
fun TopBar(
    topBarHeight: Dp = 56.dp,
    onLeftIconClick: () -> Unit = {},
    leftIcon: TopBarIcon = TopBarIcon(
        iconRes = R.drawable.ic_back,
        iconTint = White,
        onIconClick = onLeftIconClick
    ),
    rightIcon: TopBarIcon? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .singleClick(shape = CircleShape) { leftIcon.onIconClick() }
                .padding(6.dp),
            painter = painterResource(id = leftIcon.iconRes),
            tint = leftIcon.iconTint,
            contentDescription = null
        )

        if (rightIcon != null) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .singleClick(shape = CircleShape) { rightIcon.onIconClick }
                    .padding(6.dp),
                painter = painterResource(id = rightIcon.iconRes),
                tint = rightIcon.iconTint,
                contentDescription = null
            )
        }
    }
}
