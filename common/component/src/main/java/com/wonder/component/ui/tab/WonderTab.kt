package com.wonder.component.ui.tab

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
internal fun WonderTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    isTabTextEmpty: Boolean = false,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    tab: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    val styledText: @Composable (() -> Unit)? = tab?.let {
        @Composable {
            val style = MaterialTheme.typography.button.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(style, content = tab)
        }
    }
    WonderTab(
        modifier = modifier,
        selected = selected,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource,
        onClick = onClick,
        content = {
            TabBaselineLayout(
                isTabTextEmpty = isTabTextEmpty,
                icon = icon,
                text = styledText
            )
        }
    )
}

@Composable
internal fun WonderTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    TabTransition(
        activeColor = selectedContentColor,
        inactiveColor = unselectedContentColor,
        selected = selected
    ) {
        Column(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = null
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
private fun TabTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = selected, label = "")
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = TabFadeInAnimationDuration,
                    delayMillis = TabFadeInAnimationDelay,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = TabFadeOutAnimationDuration,
                    easing = LinearEasing
                )
            }
        },
        label = ""
    ) {
        if (it) activeColor else inactiveColor
    }
    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
        LocalContentAlpha provides color.alpha,
        content = content
    )
}

@Composable
private fun TabBaselineLayout(
    isTabTextEmpty: Boolean,
    text: @Composable (() -> Unit)?,
    icon: @Composable (() -> Unit)?
) {
    Layout(
        modifier = Modifier
            .padding(horizontal = if (isTabTextEmpty) 0.dp else HorizontalTabPadding),
        content = {
            if (text != null) {
                Box(
                    Modifier
                        .layoutId("text")
                        .padding(horizontal = if (isTabTextEmpty) 0.dp else HorizontalTextPadding)
                ) { text() }
            }
            if (icon != null) {
                Box(Modifier.layoutId("icon")) { icon() }
            }
        }
    ) { measurables, constraints ->
        val textPlaceable = text?.let {
            measurables.first { it.layoutId == "text" }.measure(
                constraints.copy(minHeight = 0)
            )
        }

        val iconPlaceable = icon?.let {
            measurables.first { it.layoutId == "icon" }.measure(constraints)
        }

        val tabWidth = max(textPlaceable?.width ?: 0, iconPlaceable?.width ?: 0)

        val tabHeight = if (textPlaceable != null && iconPlaceable != null) {
            LargeTabHeight
        } else {
            SmallTabHeight
        }.roundToPx()

        val firstBaseline = textPlaceable?.get(FirstBaseline)
        val lastBaseline = textPlaceable?.get(LastBaseline)

        layout(tabWidth, tabHeight) {
            when {
                textPlaceable != null && iconPlaceable != null -> placeTextAndIcon(
                    density = this@Layout,
                    textPlaceable = textPlaceable,
                    iconPlaceable = iconPlaceable,
                    tabWidth = tabWidth,
                    tabHeight = tabHeight,
                    firstBaseline = firstBaseline!!,
                    lastBaseline = lastBaseline!!
                )
                textPlaceable != null -> placeTextOrIcon(textPlaceable, tabHeight)
                iconPlaceable != null -> placeTextOrIcon(iconPlaceable, tabHeight)
                else -> {
                }
            }
        }
    }
}

private fun Placeable.PlacementScope.placeTextOrIcon(
    textOrIconPlaceable: Placeable,
    tabHeight: Int
) {
    val contentY = (tabHeight - textOrIconPlaceable.height) / 2
    textOrIconPlaceable.placeRelative(0, contentY)
}

private fun Placeable.PlacementScope.placeTextAndIcon(
    density: Density,
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    tabWidth: Int,
    tabHeight: Int,
    firstBaseline: Int,
    lastBaseline: Int
) {
    val baselineOffset = if (firstBaseline == lastBaseline) {
        SingleLineTextBaselineWithIcon
    } else {
        DoubleLineTextBaselineWithIcon
    }
    val textOffset = with(density) {
        baselineOffset.roundToPx() + TabRowDefaults.IndicatorHeight.roundToPx()
    }
    val iconOffset = with(density) {
        iconPlaceable.height + IconDistanceFromBaseline.roundToPx() - firstBaseline
    }

    val textPlaceableX = (tabWidth - textPlaceable.width) / 2
    val textPlaceableY = tabHeight - lastBaseline - textOffset
    textPlaceable.placeRelative(textPlaceableX, textPlaceableY)

    val iconPlaceableX = (tabWidth - iconPlaceable.width) / 2
    val iconPlaceableY = textPlaceableY - iconOffset
    iconPlaceable.placeRelative(iconPlaceableX, iconPlaceableY)
}

private val SmallTabHeight = 45.dp
private val LargeTabHeight = 72.dp

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

private val HorizontalTabPadding = 4.dp
private val HorizontalTextPadding = 6.dp

private val SingleLineTextBaselineWithIcon = 14.dp
private val DoubleLineTextBaselineWithIcon = 6.dp
private val IconDistanceFromBaseline = 20.sp
