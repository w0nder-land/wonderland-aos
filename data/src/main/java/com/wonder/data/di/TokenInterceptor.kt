package com.wonder.data.di

import android.accounts.AccountManager
import com.wonder.data.constant.ACCOUNT_TYPE
import com.wonder.data.constant.AccountKey
import com.wonder.data.constant.Header
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val accountManager: AccountManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val token = getAccessToken(accountManager)

        return chain.proceed(
            if (token.isEmpty()) {
                originRequest
            } else {
                chain
                    .request()
                    .newBuilder()
                    .addHeader(Header.ACCESS_TOKEN, token)
                    .url(chain.request().url)
                    .build()
            }
        )
    }

    companion object {
        fun getAccessToken(accountManager: AccountManager): String =
            accountManager.getAccountsByType(ACCOUNT_TYPE).firstOrNull()?.let { account ->
                accountManager.getUserData(account, AccountKey.ACCESS_TOKEN)
            } ?: run {
                ""
            }
    }
}
