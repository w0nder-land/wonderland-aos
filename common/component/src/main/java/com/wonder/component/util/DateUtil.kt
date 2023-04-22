package com.wonder.component.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun getCurrentMonth(): String {
    val calendar = Calendar.getInstance()
    return "${calendar[Calendar.YEAR]}년 ${calendar[Calendar.MONTH] + 1}월"
}

fun Int.dayOfWeekToString() = when (this) {
    1 -> "일"
    2 -> "월"
    3 -> "화"
    4 -> "수"
    5 -> "목"
    6 -> "금"
    7 -> "토"
    else -> ""
}

@Suppress("SimpleDateFormat")
fun getDate(pattern: String = "yyyyMMdd"): String = SimpleDateFormat(pattern).format(Date())

@Suppress("SimpleDateFormat")
fun Long.getDate(pattern: String = "yyyyMMdd"): String = SimpleDateFormat(pattern).format(this)
