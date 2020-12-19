package com.example.kotlinexample

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

open class BaseViewModel(private val schedulerProvider: BaseSchedulerProvider) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    final override fun onCleared() {
        disposables.clear()
        onViewModelCleared()
    }

    protected open fun onViewModelCleared() {}

    protected fun <T : Any> Flowable<T>.subscribeOnIO(): Flowable<T> =
        subscribeOn(schedulerProvider.io())

    protected fun <T : Any> Single<T>.subscribeOnIO(): Single<T> =
        subscribeOn(schedulerProvider.io())

    protected fun <T : Any> Maybe<T>.subscribeOnComputation(): Maybe<T> =
        subscribeOn(schedulerProvider.computation())

    protected fun <T : Any> Maybe<T>.subscribeOnIO(): Maybe<T> = subscribeOn(schedulerProvider.io())

    protected fun Completable.subscribeOnIO(): Completable = subscribeOn(schedulerProvider.io())

    protected fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}
