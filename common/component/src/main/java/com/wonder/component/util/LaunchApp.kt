package com.wonder.component.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object LaunchApp {

    fun launchBrowser(
        context: Context,
        url: String,
    ) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
