package com.imaec.auth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

fun signInKakao(
    context: Context,
    callback: (token: OAuthToken?, error: Throwable?) -> Unit
) {
    val apiClient = UserApiClient.instance
    if (apiClient.isKakaoTalkLoginAvailable(context)) {
        apiClient.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                } else {
                    apiClient.loginWithKakaoAccount(
                        context = context,
                        callback = callback
                    )
                    return@loginWithKakaoTalk
                }
            } else if (token != null) {
                callback(token, null)
            }
        }
    } else {
        apiClient.loginWithKakaoAccount(
            context = context,
            callback = callback
        )
    }
}
