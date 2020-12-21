package com.example.kotlinexample.di

import com.example.kotlinexample.BaseSchedulerProvider
import com.example.kotlinexample.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object SchedulerProvider {

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}