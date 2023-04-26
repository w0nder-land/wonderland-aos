package com.wonder.data.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

class WonderAuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val authenticator = WonderAccountAuthenticator(this)
        return authenticator.iBinder
    }
}
