package com.wonder.component.ui.switch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.resource.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WonderSwitch(
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val uncheckedThumbDiameter = ThumbDiameter
    val thumbPaddingStart = (SwitchHeight - uncheckedThumbDiameter) / 2
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() }
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scope = rememberCoroutineScope()

    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                offset.animateTo(targetValue, AnimationSpec)
            }
        }
        onDispose { }
    }

    val thumbValue = offset.asState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val thumbValueDp = with(LocalDensity.current) { thumbValue.value.toDp() }
    val thumbSizeDp = if (isPressed) {
        SwitchTokens.PressedHandleWidth
    } else {
        uncheckedThumbDiameter + (ThumbDiameter - uncheckedThumbDiameter) *
            ((thumbValueDp - thumbPaddingStart) / (ThumbPathLength - thumbPaddingStart))
    }

    val thumbOffset = if (isPressed) {
        with(LocalDensity.current) {
            if (checked) {
                ThumbPathLength - SwitchTokens.TrackOutlineWidth
            } else {
                SwitchTokens.TrackOutlineWidth
            }.toPx()
        }
    } else {
        thumbValue.value
    }
    val trackShape = SwitchTokens.TrackShape.toShape()

    Box(
        Modifier
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
            .wrapContentSize(Alignment.Center)
            .requiredSize(SwitchWidth, SwitchHeight)
    ) {
        val trackColor by rememberUpdatedState(if (checked) Wonder500 else Gray400)
        Box(
            modifier = Modifier
                .size(width = SwitchWidth, height = SwitchTokens.TrackHeight)
                .background(
                    color = trackColor,
                    shape = trackShape
                )
                .align(Alignment.Center)
        )

        val thumbColor by rememberUpdatedState(if (checked) Gray50 else Gray300)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset { IntOffset(thumbOffset.roundToInt(), 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, SwitchTokens.StateLayerSize / 2)
                )
                .requiredSize(thumbSizeDp)
                .background(
                    color = thumbColor,
                    shape = SwitchTokens.HandleShape.toShape()
                )
                .shadow(
                    elevation = 10.dp,
                    shape = SwitchTokens.HandleShape.toShape()
                ),
            contentAlignment = Alignment.Center
        ) {
            val iconColor by rememberUpdatedState(if (checked) Wonder500 else White)
            Icon(
                modifier = Modifier
                    .size(10.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_heart_fill),
                tint = iconColor,
                contentDescription = null
            )
        }
    }
}

internal val ThumbDiameter = SwitchTokens.SelectedHandleWidth
internal val UncheckedThumbDiameter = SwitchTokens.UnselectedHandleWidth
private val SwitchWidth = SwitchTokens.TrackWidth
private val SwitchHeight = 20.dp
private val ThumbPadding = (SwitchHeight - ThumbDiameter) / 2
private val ThumbPathLength = (SwitchWidth - ThumbDiameter) - ThumbPadding

private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

@Preview
@Composable
private fun WonderSwitchPreview() {
    WonderTheme {
        Column {
            WonderSwitch(
                checked = false,
                onCheckedChange = {}
            )

            WonderSwitch(
                checked = true,
                onCheckedChange = {}
            )
        }
    }
}
