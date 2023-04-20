package com.wonder.component.ui.textfield

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.Suit
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    background: Color = White,
    shape: Shape = RoundedCornerShape(8.dp),
    strokeColor: Color = Gray200,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLength: Int = 20,
    imeAction: ImeAction? = ImeAction.Search,
    keyboardActions: KeyboardActions? = null,
    textStyle: TextStyle? = null,
    fontSize: TextUnit = 16.sp,
    textColor: Color = Gray800,
    hintColor: Color = Gray400,
    height: Dp = 42.dp,
    errorText: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    iconSize: Dp = 24.dp,
    @DrawableRes icon: Int? = null,
    onValueChange: (String) -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSourceState = interactionSource.collectIsFocusedAsState()
    val scope = rememberCoroutineScope()
    val isImeVisible = WindowInsets.isImeVisible

    // Bring the composable into view (visible to user).
    LaunchedEffect(isImeVisible, interactionSourceState.value) {
        if (isImeVisible && interactionSourceState.value) {
            scope.launch {
                delay(300)
                bringIntoViewRequester.bringIntoView()
            }
        }
    }

    Column(modifier = Modifier.animateContentSize()) {
        Box(
            modifier
                .background(
                    color = background,
                    shape = shape
                )
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else strokeColor,
                    shape = shape
                )
                .height(height)
                .padding(horizontal = 16.dp),
        ) {
            BasicTextField(
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .padding(end = if (icon == null) 0.dp else 36.dp)
                    .fillMaxWidth(),
                value = text,
                singleLine = singleLine,
                textStyle = textStyle?.copy(color = textColor) ?: TextStyle(
                    fontFamily = Suit,
                    fontSize = fontSize,
                    color = textColor,
                ),
                onValueChange = {
                    if (it.length <= maxLength) {
                        onValueChange(it)
                    }
                },
                visualTransformation = visualTransformation,
                keyboardActions = keyboardActions ?: KeyboardActions(
                    onDone = { focusManager.clearFocus() },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    onSearch = { focusManager.clearFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction ?: if (singleLine) ImeAction.Done else ImeAction.Default
                ),
                interactionSource = interactionSource,
                readOnly = readOnly,
                cursorBrush = SolidColor(Wonder500),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                            .padding(
                                vertical = if (singleLine) 0.dp else 12.5.dp
                            ),
                        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart,
                    ) {
                        innerTextField()

                        if (text.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle ?: TextStyle(
                                    fontFamily = Suit,
                                    fontSize = fontSize,
                                    color = textColor,
                                ),
                                color = hintColor,
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            )

            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .align(Alignment.CenterEnd)
                        .singleClick(shape = CircleShape) { onIconClick() },
                    painter = painterResource(id = icon),
                    tint = Gray300,
                    contentDescription = null
                )
            }
        }

        if (isError && errorText.isNotBlank()) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = errorText,
                style = Body2,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String> = remember { mutableStateOf("") },
    background: Color = White,
    shape: Shape = RoundedCornerShape(8.dp),
    strokeColor: Color = Gray200,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLength: Int = 10,
    imeAction: ImeAction? = ImeAction.Search,
    keyboardActions: KeyboardActions? = null,
    textStyle: TextStyle? = null,
    fontSize: TextUnit = 16.sp,
    textColor: Color = Gray800,
    hintColor: Color = Gray400,
    height: Dp = 42.dp,
    errorText: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    iconSize: Dp = 24.dp,
    @DrawableRes icon: Int? = null,
    onValueChange: (String) -> Unit = {},
    onIconClick: () -> Unit = {}
) {
    BasicTextField(
        modifier = modifier,
        text = textState.value,
        background = background,
        shape = shape,
        strokeColor = strokeColor,
        placeholder = placeholder,
        keyboardType = keyboardType,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLength = maxLength,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        textStyle = textStyle,
        fontSize = fontSize,
        textColor = textColor,
        hintColor = hintColor,
        height = height,
        errorText = errorText,
        isError = isError,
        visualTransformation = visualTransformation,
        iconSize = iconSize,
        icon = icon,
        onValueChange = {
            if (it.length <= maxLength) {
                textState.value = it
                onValueChange(it)
            }
        },
        onIconClick = onIconClick
    )
}

@Preview(showBackground = true)
@Composable
private fun BasicTextFieldPreview() {
    WonderTheme {
        BasicTextField(
            modifier = Modifier.padding(16.dp),
            textState = remember { mutableStateOf("textField") },
            placeholder = "placeholder",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicTextFieldWithIconPreview() {
    WonderTheme {
        BasicTextField(
            modifier = Modifier.padding(16.dp),
            textState = remember { mutableStateOf("textField") },
            placeholder = "placeholder",
            icon = R.drawable.ic_search
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun BasicTextFieldOnSearchScreenPreview() {
    WonderTheme {
        BasicTextField(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textState = remember { mutableStateOf("") },
            textStyle = Subtitle3,
            hintColor = Gray400,
            background = Gray700,
            strokeColor = Gray700,
            shape = CircleShape,
            placeholder = "어떤 축제를 찾고 계신가요?",
            icon = R.drawable.ic_search
        )
    }
}
