package com.wonder.component.ui.topbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Primary
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    title: String = "",
    topBarColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    collapsedTitleStyle: TextStyle = TextStyle(),
    expandedTitleStyle: TextStyle = TextStyle(),
    expandedTitleStartPadding: Dp = 16.dp,
    expandedTitleBottomPadding: Dp = 16.dp,
    minHeight: Dp = 56.dp,
    isEnableFling: Boolean = false,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    var collapsingContentHeight by remember { mutableStateOf(0) }
    val offset = scrollBehavior.state.heightOffset

    val minHeightPx = LocalDensity.current.run { minHeight.toPx() }

    SideEffect {
        val limit = minHeightPx - collapsingContentHeight.toFloat()
        if (scrollBehavior.state.heightOffsetLimit != limit) {
            scrollBehavior.state.heightOffsetLimit = limit
        }
    }

    val appBarDragModifier = if (!scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = offset + delta
            },
            onDragStopped = { velocity ->
                if (isEnableFling) {
                    settleAppBar(
                        scrollBehavior.state,
                        velocity,
                        scrollBehavior.flingAnimationSpec,
                        scrollBehavior.snapAnimationSpec
                    )
                }
            }
        )
    } else {
        Modifier
    }

    val baseOffset = if (scrollBehavior.state.heightOffsetLimit > minHeightPx) {
        -minHeightPx
    } else {
        (minHeightPx - collapsingContentHeight)
    }
    val titleCollapsedFraction = offset / baseOffset
    val collapsedAlpha =
        CubicBezierEasing(.8f, 0f, .8f, .15f).transform(titleCollapsedFraction)
    val expandedAlpha = 1f - titleCollapsedFraction

    Surface(
        modifier = modifier
            .background(backgroundColor)
            .systemBarsPadding()
            .statusBarsPadding()
            .then(appBarDragModifier)
    ) {
        Layout(
            content = {
                Box(modifier = Modifier.layoutId("collapsingContent")) { content() }
                Box(
                    Modifier
                        .layoutId("toolbar")
                        .fillMaxWidth()
                        .height(minHeight)
                        .background(topBarColor.copy(alpha = collapsedAlpha))
                ) {
                    topBar()
                }
                Text(
                    modifier = Modifier
                        .layoutId("expandedTitle")
                        .alpha(expandedAlpha)
                        .padding(
                            top = 3.dp,
                            start = expandedTitleStartPadding,
                            bottom = expandedTitleBottomPadding
                        ),
                    text = title, color = Primary, style = expandedTitleStyle
                )
                Text(
                    modifier = Modifier
                        .layoutId("collapsedTitle")
                        .alpha(collapsedAlpha),
                    text = title, color = Primary, style = collapsedTitleStyle
                )
            },
            modifier = Modifier
                .statusBarsPadding()
                .systemBarsPadding(),
        ) { measurables, constraints ->
            val ccPlaceable =
                measurables.first { it.layoutId == "collapsingContent" }.measure(constraints)
            val tbPlaceable =
                measurables.first { it.layoutId == "toolbar" }.measure(constraints)
            val titlePlaceable =
                measurables.first { it.layoutId == "expandedTitle" }.measure(constraints)
            val collapsedTitlePlaceable =
                measurables.first { it.layoutId == "collapsedTitle" }.measure(constraints)

            collapsingContentHeight =
                max(ccPlaceable.height, tbPlaceable.height + titlePlaceable.height)

            val maxWidth =
                listOf(ccPlaceable.width, tbPlaceable.width).max()
            val currentHeight =
                collapsingContentHeight + scrollBehavior.state.heightOffset

            layout(maxWidth, currentHeight.toInt()) {
                ccPlaceable.placeRelative(
                    0, offset.roundToInt()
                )
                titlePlaceable.placeRelative(
                    0,
                    minHeight.roundToPx() + offset.roundToInt()
                )
                tbPlaceable.placeRelative(0, 0)
                collapsedTitlePlaceable.placeRelative(
                    (maxWidth - collapsedTitlePlaceable.width) / 2,
                    (minHeightPx - collapsedTitlePlaceable.height).toInt() / 2
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun platCollapsedScrollBehavior(
    state: TopAppBarState = rememberTopAppBarState(),
    canScroll: () -> Boolean = { true },
    snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
    flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
    isEnableFling: Boolean = false
): TopAppBarScrollBehavior =
    PlatCollapsedScrollBehavior(
        state = state,
        snapAnimationSpec = snapAnimationSpec,
        flingAnimationSpec = flingAnimationSpec,
        canScroll = canScroll,
        isEnableFling = isEnableFling
    )

@OptIn(ExperimentalMaterial3Api::class)
private class PlatCollapsedScrollBehavior(
    override val state: TopAppBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
    val canScroll: () -> Boolean = { true },
    isEnableFling: Boolean
) : TopAppBarScrollBehavior {
    override val isPinned: Boolean = false
    override var nestedScrollConnection =
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (!canScroll() || available.y > 0f) return Offset.Zero

                val prevHeightOffset = state.heightOffset
                state.heightOffset = state.heightOffset + available.y
                return if (prevHeightOffset != state.heightOffset) {
                    available.copy(x = 0f)
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canScroll()) return Offset.Zero
                state.contentOffset += consumed.y

                if (available.y < 0f || consumed.y < 0f) {
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset = state.heightOffset + consumed.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }

                if (consumed.y == 0f && available.y > 0) {
                    state.contentOffset = 0f
                }

                if (available.y > 0f) {
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset = state.heightOffset + available.y
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val superConsumed = super.onPostFling(consumed, available)
                return if (isEnableFling) {
                    superConsumed + settleAppBar(
                        state,
                        available.y,
                        flingAnimationSpec,
                        snapAnimationSpec
                    )
                } else {
                    superConsumed
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}
