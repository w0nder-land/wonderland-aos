package com.wonder.component.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun getCurrentYearMonth(): String {
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

@SuppressLint("SimpleDateFormat")
fun Date.toYear(): Int = SimpleDateFormat("yyyy").format(this).toInt()

@SuppressLint("SimpleDateFormat")
fun Date.toMonth(): Int = SimpleDateFormat("M").format(this).toInt()

@SuppressLint("SimpleDateFormat")
fun Date.toDay(): Int = SimpleDateFormat("d").format(this).toInt()

@SuppressLint("SimpleDateFormat")
fun Date.toDateString(pattern: String = "yyyyMMdd"): String = SimpleDateFormat(pattern).format(this)

fun Date.toCalendar(): Calendar = Calendar.getInstance().apply {
    time = this@toCalendar
}

fun Calendar.year(): Int = get(Calendar.YEAR)

fun Calendar.month(): Int = get(Calendar.MONTH) + 1

fun Calendar.dayOfYear(): Int = get(Calendar.DAY_OF_YEAR)

fun Calendar.dayOfMonth(): Int = get(Calendar.DAY_OF_MONTH)

fun Calendar.dayOfWeek(): Int = get(Calendar.DAY_OF_WEEK)

fun Calendar.addMonth(amount: Int): Calendar = apply {
    add(Calendar.MONTH, amount)
}

fun Calendar.addDayOfYear(amount: Int): Calendar = apply {
    add(Calendar.DAY_OF_YEAR, amount)
}

fun Calendar.addDayOfMonth(amount: Int): Calendar = apply {
    add(Calendar.DAY_OF_MONTH, amount)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(pattern: String = "yyyyMMdd"): Date =
    SimpleDateFormat(pattern).parse(this) ?: Date()
