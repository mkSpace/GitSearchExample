package com.example.kotlinexample.di

import com.example.kotlinexample.api.SearchService
import com.example.kotlinexample.api.UserService
import com.example.kotlinexample.network.SampleRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ApplicationComponent::class)
object ServiceModule {

    @Provides
    fun provideSearchService(okhttpClient: OkHttpClient): SearchService =
        SampleRetrofit.create(okhttpClient)

    @Provides
    fun provideUserService(okhttpClient: OkHttpClient): UserService =
        SampleRetrofit.create(okhttpClient)
}