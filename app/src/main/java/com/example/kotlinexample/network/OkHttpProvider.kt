package com.example.kotlinexample.network

import com.example.kotlinexample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpProvider {

    private const val TIMEOUT_CONNECT = 10L

    val gitHubApi: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .addInterceptor(createLoggingInterceptor())
        .build()

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}