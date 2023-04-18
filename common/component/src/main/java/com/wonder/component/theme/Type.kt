package com.wonder.component.theme

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wonder.resource.R

val Suit = FontFamily(
    Font(R.font.suit_bold, FontWeight.W700),
    Font(R.font.suit_semibold, FontWeight.W600),
    Font(R.font.suit_regular, FontWeight.W400)
)

val Heading1 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W700,
    fontSize = 24.sp,
    lineHeight = 36.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Heading2 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W700,
    fontSize = 20.sp,
    lineHeight = 30.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Heading3 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W700,
    fontSize = 18.sp,
    lineHeight = 27.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Subtitle1 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    lineHeight = 27.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Subtitle2 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W600,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Subtitle3 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W600,
    fontSize = 14.sp,
    lineHeight = 21.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Body1 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Body2 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 21.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Caption1 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W700,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val Caption2 = TextStyle(
    fontFamily = Suit,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)
