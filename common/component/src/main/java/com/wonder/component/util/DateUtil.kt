package com.wonder.component.util

import java.text.SimpleDateFormat
import java.util.Date

@Suppress("SimpleDateFormat")
fun getDate(pattern: String = "yyyyMMdd"): String = SimpleDateFormat(pattern).format(Date())

@Suppress("SimpleDateFormat")
fun Long.getDate(pattern: String = "yyyyMMdd"): String = SimpleDateFormat(pattern).format(this)
