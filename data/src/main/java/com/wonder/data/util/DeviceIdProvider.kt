package com.wonder.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import java.lang.Exception
import java.util.UUID

object DeviceIdProvider {

    @SuppressLint("MissingPermission", "HardwareIds")
    fun getDeviceId(context: Context): String {
        val SSAID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SSAID
        } else {
            return try {
                val tm = ContextCompat.getSystemService(context, TelephonyManager::class.java)
                if (tm != null) {
                    val tmDevice = tm.deviceId
                    val tmSerial = tm.simSerialNumber
                    val deviceUuid =
                        UUID(
                            SSAID.hashCode().toLong(),
                            tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong()
                        )
                    deviceUuid.toString()
                } else {
                    SSAID
                }
            } catch (e: Exception) {
                SSAID
            }
        }
    }
}
