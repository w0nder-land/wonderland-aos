package com.wonder.component.ui.switch

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal object SwitchTokens {
    val DisabledSelectedHandleColor = ColorSchemeKeyTokens.Surface
    const val DisabledSelectedHandleOpacity = 1.0f
    val DisabledSelectedIconColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledSelectedIconOpacity = 0.38f
    val DisabledSelectedTrackColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledTrackOpacity = 0.12f
    val DisabledUnselectedHandleColor = ColorSchemeKeyTokens.OnSurface
    const val DisabledUnselectedHandleOpacity = 0.38f
    val DisabledUnselectedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    const val DisabledUnselectedIconOpacity = 0.38f
    val DisabledUnselectedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val DisabledUnselectedTrackOutlineColor = ColorSchemeKeyTokens.OnSurface
    val HandleShape = ShapeKeyTokens.CornerFull
    val PressedHandleHeight = 28.0.dp
    val PressedHandleWidth = 28.0.dp
    val SelectedFocusHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedFocusIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedFocusTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedHandleColor = ColorSchemeKeyTokens.OnPrimary
    val SelectedHandleHeight = 24.0.dp
    val SelectedHandleWidth = 20.dp
    val SelectedHoverHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedHoverIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedHoverTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedIconSize = 16.0.dp
    val SelectedPressedHandleColor = ColorSchemeKeyTokens.PrimaryContainer
    val SelectedPressedIconColor = ColorSchemeKeyTokens.OnPrimaryContainer
    val SelectedPressedTrackColor = ColorSchemeKeyTokens.Primary
    val SelectedTrackColor = ColorSchemeKeyTokens.Primary
    val StateLayerShape = ShapeKeyTokens.CornerFull
    val StateLayerSize = 40.0.dp
    val TrackHeight = 14.dp
    val TrackOutlineWidth = 2.0.dp
    val TrackShape = ShapeKeyTokens.CornerFull
    val TrackWidth = 36.dp
    val UnselectedFocusHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedFocusIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedFocusTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedFocusTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedHandleColor = ColorSchemeKeyTokens.Outline
    val UnselectedHandleHeight = 16.0.dp
    val UnselectedHandleWidth = 16.0.dp
    val UnselectedHoverHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedHoverIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedHoverTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedHoverTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedIconSize = 16.0.dp
    val UnselectedPressedHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val UnselectedPressedIconColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedPressedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedPressedTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val UnselectedTrackColor = ColorSchemeKeyTokens.SurfaceVariant
    val UnselectedTrackOutlineColor = ColorSchemeKeyTokens.Outline
    val IconHandleHeight = 24.0.dp
    val IconHandleWidth = 24.0.dp
}

internal fun CornerBasedShape.top(): CornerBasedShape {
    return copy(bottomStart = CornerSize(0.0.dp), bottomEnd = CornerSize(0.0.dp))
}

internal fun CornerBasedShape.end(): CornerBasedShape {
    return copy(topStart = CornerSize(0.0.dp), bottomStart = CornerSize(0.0.dp))
}

internal fun Shapes.fromToken(value: ShapeKeyTokens): Shape {
    return when (value) {
        ShapeKeyTokens.CornerExtraLarge -> extraLarge
        ShapeKeyTokens.CornerExtraLargeTop -> extraLarge.top()
        ShapeKeyTokens.CornerExtraSmall -> extraSmall
        ShapeKeyTokens.CornerExtraSmallTop -> extraSmall.top()
        ShapeKeyTokens.CornerFull -> CircleShape
        ShapeKeyTokens.CornerLarge -> large
        ShapeKeyTokens.CornerLargeEnd -> large.end()
        ShapeKeyTokens.CornerLargeTop -> large.top()
        ShapeKeyTokens.CornerMedium -> medium
        ShapeKeyTokens.CornerNone -> RectangleShape
        ShapeKeyTokens.CornerSmall -> small
    }
}

@Composable
internal fun ShapeKeyTokens.toShape(): Shape {
    return MaterialTheme.shapes.fromToken(this)
}
