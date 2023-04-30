package com.wonder.wonderland.presentation.calendar.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
internal fun CalendarFilterButton(
    modifier: Modifier,
    onFilterClick: () -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .size(width = 83.dp, height = 44.dp)
            .background(
                color = if (isSelected) White else Gray900,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = White,
                shape = CircleShape
            )
            .singleClick(shape = CircleShape) {
                onFilterClick()
                isSelected = !isSelected
            }
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_filter),
                tint = if (isSelected) Gray900 else White,
                contentDescription = null
            )

            Text(
                text = "필터",
                style = Subtitle3,
                color = if (isSelected) Gray900 else White
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp, top = 11.dp)
                    .size(6.dp)
                    .background(
                        color = Wonder500,
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview
@Composable
private fun CalendarFilterButtonPreview() {
    WonderTheme {
        CalendarFilterButton(
            modifier = Modifier,
            onFilterClick = {}
        )
    }
}
