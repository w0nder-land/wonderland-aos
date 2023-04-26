package com.wonder.data.repository

import android.accounts.Account
import android.accounts.AccountManager
import androidx.core.os.bundleOf
import com.wonder.data.constant.ACCOUNT_TYPE
import com.wonder.data.constant.AccountKey
import com.wonder.data.remote.auth.AuthRemoteDataSource
import com.wonder.domain.model.auth.UserAuthInfo
import com.wonder.domain.model.member.UserInfo
import com.wonder.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val accountManager: AccountManager,
    private val account: Account?
) : AuthRepository {

    override val userInfo: UserInfo?
        get() = if (account != null) {
            try {
                accountToUserInfo(accountManager, account)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }

    override val isSignIn: Boolean
        get() = userInfo != null

    override suspend fun signInKakao(token: String): UserAuthInfo {
        val userAuthInfo = authRemoteDataSource.signInKakao(token).also {
            updateToken(it)
        }
        updateToken(userAuthInfo)
        return userAuthInfo
    }

    private fun updateToken(userAuthInfo: UserAuthInfo): Boolean {
        val userId = userAuthInfo.userId

        if (account != null) {
            accountManager.removeAccountExplicitly(account)
        }

        return accountManager.addAccountExplicitly(
            Account(userId.toString(), ACCOUNT_TYPE),
            null,
            bundleOf(
                AccountKey.USER_ID to userId.toString(),
                AccountKey.NICKNAME to userAuthInfo.nickname,
                AccountKey.SOCIAL_TYPE to userAuthInfo.socialType,
                AccountKey.ACCESS_TOKEN to userAuthInfo.token,
                AccountKey.ACCESS_TOKEN_EXPIRED_AT to userAuthInfo.tokenExpiredAt,
                AccountKey.REFRESH_TOKEN to userAuthInfo.refreshToken,
                AccountKey.REFRESH_TOKEN_EXPIRED_AT to userAuthInfo.refreshTokenExpiredAt
            )
        )
    }

    private fun accountToUserInfo(accountManager: AccountManager, account: Account): UserInfo =
        UserInfo(
            userId = accountManager.getUserData(account, AccountKey.USER_ID).toInt(),
            nickname = accountManager.getUserData(account, AccountKey.NICKNAME)
        )
}
