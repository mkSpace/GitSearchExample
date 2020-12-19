package com.example.kotlinexample.search

import androidx.hilt.lifecycle.ViewModelInject
import com.example.kotlinexample.BaseSchedulerProvider
import com.example.kotlinexample.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class SearchViewModel @ViewModelInject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val searchRepository: SearchRepository
) : BaseViewModel(schedulerProvider) {

    private val selectedRepositoryProcessor = PublishProcessor.create<Repository>()

    val selectedRepository: Flowable<Repository> =
        selectedRepositoryProcessor.distinctUntilChanged()

    fun setQuery(query: String) {
        if (query.isNotBlank()) {
            searchRepository.setQuery(query)
        }
    }

    fun selectRepository(repository: Repository): Completable =
        searchRepository.insertRepositoryIntoDatabase(repository)
            .andThen(Completable.fromAction { selectedRepositoryProcessor.offer(repository) })
            .subscribeOnIO()

    val itemsByQuery = searchRepository.loadItemsByQuery().subscribeOnIO()

    val initialLoad = searchRepository.initialLoad
}