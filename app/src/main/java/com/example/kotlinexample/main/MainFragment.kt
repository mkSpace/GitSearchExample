package com.example.kotlinexample.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.kotlinexample.BaseFragment
import com.example.kotlinexample.Constants
import com.example.kotlinexample.R
import com.example.kotlinexample.rx.observeOnMain
import com.example.kotlinexample.rx.subscribeWithErrorLogger
import com.example.kotlinexample.search.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment(), MainAdapter.OnClickListener {

    @Inject
    lateinit var adapter: MainAdapter

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            mainViewModel.navigateNextStep(Step.SEARCH, Bundle())
        }
        mainRepositories.adapter = adapter
        toolbar.title = context?.getString(R.string.app_name)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.items
            .observeOnMain()
            .subscribeWithErrorLogger {
                mainRepositories.isVisible = it.isNotEmpty()
                mainEmptyText.isVisible = it.isEmpty()
                adapter.submitList(it)
            }
            .addToDisposables()
    }

    override fun onRepositoryClick(repository: Repository) {
        mainViewModel.navigateNextStep(
            Step.DETAIL, bundleOf(
                Constants.REPOSITORY_ID to repository.id,
                Constants.USER_NAME to repository.owner.userName
            )
        )
    }
}