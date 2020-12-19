package com.example.kotlinexample.di

import com.example.kotlinexample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val TIMEOUT_CONNECT = 10L

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .addInterceptor(createLoggingInterceptor())
        .build()
}