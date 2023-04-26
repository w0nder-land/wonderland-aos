package com.wonder.data.di

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.wonder.data.constant.ACCOUNT_TYPE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthModule {

    @Provides
    @Singleton
    fun provideAccountManager(@ApplicationContext context: Context): AccountManager =
        AccountManager.get(context)

    @Provides
    @Singleton
    fun provideAccount(accountManager: AccountManager): Account? =
        accountManager.getAccountsByType(ACCOUNT_TYPE).firstOrNull()
}
