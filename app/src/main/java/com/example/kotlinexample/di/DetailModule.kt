package com.example.kotlinexample.di

import androidx.fragment.app.Fragment
import com.example.kotlinexample.detail.DetailAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DetailModule {

    @Provides
    fun provideDetailAdapter(fragment: Fragment) : DetailAdapter =
        DetailAdapter(fragment as? DetailAdapter.OnClickListener)
}