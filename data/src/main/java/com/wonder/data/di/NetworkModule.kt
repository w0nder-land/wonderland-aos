package com.wonder.data.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.wonder.data.BuildConfig
import com.wonder.data.constant.HOST_WONDERLAND
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
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
