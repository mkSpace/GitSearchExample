package com.example.kotlinexample.di

import android.content.Context
import com.example.kotlinexample.room.SampleDatabase
import com.example.kotlinexample.search.RepositoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun provideSampleDatabase(
        @ApplicationContext context: Context
    ): SampleDatabase = SampleDatabase.getInstance(context)

    @Provides
    fun provideSearchDao(
        @ApplicationContext context: Context
    ): RepositoryDao = SampleDatabase.getInstance(context).searchDao()

}