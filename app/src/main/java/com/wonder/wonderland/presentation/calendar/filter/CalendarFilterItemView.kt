package com.wonder.wonderland.presentation.calendar.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Wonder500
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
fun CalendarFilterItemView(
    title: String,
    count: Int,
    isSelected: Boolean,
    width: Dp = 257.dp,
    onFilterItemClick: () -> Unit,
) {
    Box {
        Row(
            modifier = Modifier
                .width(width)
                .height(44.dp)
                .padding(start = 44.dp)
                .singleClick(hasRipple = false) { onFilterItemClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Body1,
                color = if (isSelected) Wonder500 else Gray200
            )

            Text(
                text = "$count",
                style = Caption1,
                color = if (isSelected) Wonder500 else Gray300
            )
        }

        if (isSelected) {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(16.dp)
                    .align(Alignment.CenterStart),
                painter = painterResource(id = R.drawable.ic_check),
                tint = Wonder500,
                contentDescription = null
            )
        }
    }
}
