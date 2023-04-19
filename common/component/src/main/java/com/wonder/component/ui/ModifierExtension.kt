package com.wonder.component.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import com.wonder.component.util.ClickableCheckObject
import kotlinx.coroutines.launch

/** 중복클릭 방지 */
fun Modifier.singleClick(
    shape: Shape = RectangleShape,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    hasRipple: Boolean = true,
    onClick: () -> Unit,
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val scope = rememberCoroutineScope()
    Modifier
        .clip(shape)
        .clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = {
                scope.launch {
                    ClickableCheckObject.throttledFirstClicks(onClick)
                }
            },
            role = role,
            indication = if (hasRipple) rememberRipple() else null,
            interactionSource = remember { MutableInteractionSource() }
        )
}
