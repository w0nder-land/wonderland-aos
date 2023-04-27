package com.wonder.data.di

import android.accounts.AccountManager
import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.wonder.data.BuildConfig
import com.wonder.data.constant.HOST_WONDERLAND
import com.wonder.data.constant.Header
import com.wonder.data.constant.HeaderValue
import com.wonder.data.util.DeviceIdProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideHeaderInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(Header.CLIENT_ID, HeaderValue.CLIENT_ID)
                .addHeader(Header.UNIQUE_ID, DeviceIdProvider.getDeviceId(context))
                .build()
            return@Interceptor chain.proceed(request)
        }

    @Singleton
    @Provides
    fun provideTokenInterceptor(accountManager: AccountManager): TokenInterceptor =
        TokenInterceptor(accountManager)

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith("{") && !message.startsWith("[")) {
                    Timber.tag("OkHttp").d(message)
                    return
                }

                try {
                    Timber.tag("OkHttp").d(
                        "--> RESPONSE\n${
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(JsonParser().parse(message))
                        }\n<-- RESPONSE"
                    )
                } catch (m: JsonSyntaxException) {
                    Timber.tag("OkHttp").d(message)
                }
            }
        }
    ).apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: Interceptor,
        tokenInterceptor: TokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor(tokenInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit = Retrofit
        .Builder()
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .baseUrl(HOST_WONDERLAND)
        .build()
}
