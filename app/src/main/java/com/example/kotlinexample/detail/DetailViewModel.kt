package com.example.kotlinexample.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.example.kotlinexample.BaseSchedulerProvider
import com.example.kotlinexample.BaseViewModel
import com.example.kotlinexample.Constants
import com.example.kotlinexample.rx.subscribeWithErrorLogger
import com.example.kotlinexample.search.Repository
import com.example.kotlinexample.search.SearchRepository
import com.example.kotlinexample.user.UserRepository
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.combineLatest

class DetailViewModel @ViewModelInject constructor(
    schedulerProvider: BaseSchedulerProvider,
    searchRepository: SearchRepository,
    userRepository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(schedulerProvider) {

    private val repositoryId: String by lazy { savedStateHandle.get(Constants.REPOSITORY_ID) ?: "" }
    private val userName: String by lazy { savedStateHandle.get(Constants.USER_NAME) ?: "" }

    private val repositoryProcessor = BehaviorProcessor.create<Repository>()
    private val followersProcessor: BehaviorProcessor<List<Repository.Owner>> =
        BehaviorProcessor.createDefault(emptyList())
    private val followingProcessor: BehaviorProcessor<List<Repository.Owner>> =
        BehaviorProcessor.createDefault(emptyList())

    private val itemsProcessor: BehaviorProcessor<List<DetailAdapterItem>> =
        BehaviorProcessor.create()

    val ownerName = repositoryProcessor.distinctUntilChanged().map { it.owner.userName }

    val items: Flowable<List<DetailAdapterItem>> = itemsProcessor.distinctUntilChanged()

    init {

        searchRepository.getRepositorySingle(repositoryId)
            .subscribeOnIO()
            .subscribeWithErrorLogger { repositoryProcessor.offer(it) }
            .addToDisposables()

        userRepository.loadFollowers(userName)
            .subscribeOnIO()
            .subscribeWithErrorLogger { followersProcessor.offer(it) }
            .addToDisposables()

        userRepository.loadFollowing(userName)
            .subscribeOnIO()
            .subscribeWithErrorLogger { followingProcessor.offer(it) }
            .addToDisposables()

        repositoryProcessor
            .combineLatest(followersProcessor, followingProcessor)
            .subscribeOnIO()
            .subscribeWithErrorLogger { (repository, followers, following) ->
                val followUsers = listOfNotNull(
                    if (followers.isNotEmpty()) followers.toDetailAdapterItem(false) else null,
                    if (following.isNotEmpty()) following.toDetailAdapterItem(true) else null
                )
                val items = listOf(
                    repository.toDetailAdapterItems(),
                    if (!followUsers.isNullOrEmpty()) followUsers else emptyList()
                ).flatten()

                itemsProcessor.offer(items)
            }
            .addToDisposables()
    }

}