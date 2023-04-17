package com.wonder.wonderland.core.designsystem

import android.content.res.Resources
import android.util.DisplayMetrics

fun Float.dpToPx(): Int {
    val dpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
    val dp = this * (dpi / DisplayMetrics.DENSITY_DEFAULT)
    return dp.toInt()
}

fun Int.dpToPx(): Int {
    val dpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
    val dp = this * (dpi / DisplayMetrics.DENSITY_DEFAULT)
    return dp.toInt()
}
