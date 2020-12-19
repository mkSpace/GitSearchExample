package com.example.kotlinexample.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlinexample.BaseActivity
import com.example.kotlinexample.R
import com.example.kotlinexample.detail.DetailFragment
import com.example.kotlinexample.rx.observeOnMain
import com.example.kotlinexample.rx.subscribeWithErrorLogger
import com.example.kotlinexample.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModels()
    }

    private fun bindViewModels() {
        mainViewModel.currentStepWithBundle
            .observeOnMain()
            .subscribeWithErrorLogger { (step, bundle) ->
                navigate(step, bundle)
            }
            .addToDisposables()
    }

    private fun navigate(step: Step, bundle: Bundle) {
        val fragment = when (step) {
            Step.MAIN -> MainFragment()
            Step.SEARCH -> SearchFragment()
            Step.DETAIL -> DetailFragment()
        }.apply { arguments = bundle }

        val currentFragment = supportFragmentManager.primaryNavigationFragment

        if (currentFragment == null || currentFragment.javaClass != fragment.javaClass) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .setPrimaryNavigationFragment(fragment)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
        }
    }
}
