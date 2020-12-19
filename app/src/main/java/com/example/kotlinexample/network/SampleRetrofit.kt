package com.example.kotlinexample.network

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

object SampleRetrofit {

    private const val BASE_URL = "https://api.github.com/"

    fun <T : Any> create(
        service: KClass<T>,
        client: OkHttpClient
    ): T = create(service.java, client)

    fun <T> create(
        service: Class<T>,
        client: OkHttpClient
    ): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    inline fun <reified T : Any> create(okHttpClient: OkHttpClient): T {
        return create(T::class.java, okHttpClient)
    }
}