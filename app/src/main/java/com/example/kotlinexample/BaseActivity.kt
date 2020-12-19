package com.example.kotlinexample

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseActivity : AppCompatActivity() {

    private val disposables by lazy { CompositeDisposable() }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    protected fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}
