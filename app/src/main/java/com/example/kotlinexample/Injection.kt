package com.example.kotlinexample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinexample.api.SearchService
import com.example.kotlinexample.api.UserService
import com.example.kotlinexample.detail.DetailViewModel
import com.example.kotlinexample.main.MainViewModel
import com.example.kotlinexample.network.SampleRetrofit
import com.example.kotlinexample.room.SampleDatabase
import com.example.kotlinexample.search.RepositoryDao
import com.example.kotlinexample.search.SearchRemoteDataSource
import com.example.kotlinexample.search.SearchRepository
import com.example.kotlinexample.search.SearchViewModel
import com.example.kotlinexample.user.UserRemoteDataSource
import com.example.kotlinexample.user.UserRepository

object Injection {

    private fun getSearchService(): SearchService = SampleRetrofit.create(SearchService::class)

    private fun getUserService(): UserService = SampleRetrofit.create(UserService::class)

    private fun getSearchDao(context: Context): RepositoryDao =
        SampleDatabase.getInstance(context).searchDao()

    private fun getSearchRemoteDataSource(): SearchRemoteDataSource =
        SearchRemoteDataSource(getSearchService())

    private fun getUserRemoteDataSource(): UserRemoteDataSource =
        UserRemoteDataSource(getUserService())

    fun getSearchRepository(context: Context): SearchRepository = SearchRepository(
        remote = getSearchRemoteDataSource(),
        repoDao = getSearchDao(context)
    )

    fun getUserRepository(): UserRepository = UserRepository(remote = getUserRemoteDataSource())

    @Suppress("UNCHECKED_CAST")
    fun provideMainViewModelFactory(context: Context): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainViewModel(
                    schedulerProvider = SchedulerProvider,
                    searchRepository = getSearchRepository(context)
                ) as T
        }

    @Suppress("UNCHECKED_CAST")
    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                SearchViewModel(SchedulerProvider, getSearchRepository(context)) as T
        }

    @Suppress("UNCHECKED_CAST")
    fun provideDetailViewModelFactory(
        context: Context,
        repoId: String,
        userName: String
    ): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DetailViewModel(
                    schedulerProvider = SchedulerProvider,
                    repositoryId = repoId,
                    userName = userName,
                    searchRepository = getSearchRepository(context),
                    userRepository = getUserRepository()
                ) as T
        }
}