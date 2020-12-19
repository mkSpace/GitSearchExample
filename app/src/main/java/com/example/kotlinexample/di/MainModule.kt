package com.example.kotlinexample.di

import androidx.fragment.app.Fragment
import com.example.kotlinexample.main.MainAdapter
import com.example.kotlinexample.search.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object MainModule {

    @Provides
    fun provideMainAdapter() : MainAdapter =
        MainAdapter()
}