package com.example.kotlinexample

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo


abstract class BaseFragment : Fragment() {
    private val disposables by lazy { CompositeDisposable() }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    protected fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}