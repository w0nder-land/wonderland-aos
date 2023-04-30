package com.wonder.data.di

import android.accounts.AccountManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.imaec.model.common.NetworkError
import com.wonder.data.BuildConfig
import com.wonder.data.constant.HEADER
import com.wonder.data.constant.HOST_WONDERLAND
import com.wonder.data.constant.Header
import com.wonder.data.constant.HeaderValue
import com.wonder.data.constant.NETWORK_ERROR
import com.wonder.data.util.DeviceIdProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkErrorEvent(): MutableSharedFlow<String> = MutableSharedFlow()

    @Singleton
    @Provides
    @Named(HEADER)
    fun provideHeaderInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(Header.CLIENT_ID, HeaderValue.CLIENT_ID)
                .addHeader(Header.UNIQUE_ID, DeviceIdProvider.getDeviceId(context))
                .addHeader(Header.ACCESS_TOKEN, "")
                .build()
            return@Interceptor chain.proceed(request)
        }

    @Singleton
    @Provides
    fun provideTokenInterceptor(accountManager: AccountManager): TokenInterceptor =
        TokenInterceptor(accountManager)

    @Provides
    @Singleton
    @Named(NETWORK_ERROR)
    fun providesNetworkErrorInterceptor(
        networkErrorEvent: MutableSharedFlow<String>
    ): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        handleNetworkError(response, networkErrorEvent)

        response
    }

    private fun handleNetworkError(
        response: Response,
        networkErrorEvent: MutableSharedFlow<String>
    ) {
        val bodyString = response.peekBody(Long.MAX_VALUE).string()
        val networkError = when (response.code) {
            400 -> {
                try {
                    Gson().fromJson(bodyString, NetworkError::class.java).message
                } catch (e: Exception) {
                    null
                }
            }
            500 -> {
                "오류가 발생 했습니다."
            }
            else -> null
        }
        if (networkError != null) {
            CoroutineScope(Dispatchers.Main).launch {
                networkErrorEvent.emit(networkError)
            }
        }
    }

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
        @Named(HEADER) headerInterceptor: Interceptor,
        @Named(NETWORK_ERROR) networkErrorInterceptor: Interceptor,
        tokenInterceptor: TokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(networkErrorInterceptor)
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
