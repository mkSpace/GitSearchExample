package com.example.kotlinexample.search

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kotlinexample.BaseFragment
import com.example.kotlinexample.Injection
import com.example.kotlinexample.R
import com.example.kotlinexample.Constants
import com.example.kotlinexample.extensions.hideSoftInput
import com.example.kotlinexample.main.MainViewModel
import com.example.kotlinexample.main.Step
import com.example.kotlinexample.rx.observeOnMain
import com.example.kotlinexample.rx.subscribeWithErrorLogger
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    private val searchViewModel by viewModels<SearchViewModel> {
        Injection.provideSearchViewModelFactory(requireContext())
    }
    private val mainViewModel by activityViewModels<MainViewModel> {
        Injection.provideMainViewModelFactory(requireContext())
    }

    private lateinit var adapter: SearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = SearchAdapter(::handleRepositoryItemClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRepositories.adapter = adapter
        setupToolbar()
    }

    private fun setupToolbar() {
        val searchManager =
            getSystemService(requireContext(), SearchManager::class.java) as SearchManager
        val searchView = (toolbar.menu.findItem(R.id.item_search).actionView as SearchView)
        val searchableInfo = searchManager.getSearchableInfo(
            ComponentName(
                requireContext(),
                requireContext().javaClass.name
            )
        )

        searchView.apply {
            setSearchableInfo(searchableInfo)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchViewModel.setQuery(it) }
                    hideSoftInput()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchViewModel.itemsByQuery
            .observeOnMain()
            .subscribeWithErrorLogger {
                searchEmptyText.isVisible = it.isEmpty()
                searchRepositories.isVisible = it.isNotEmpty()
                adapter.submitList(it)
            }
            .addToDisposables()

        searchViewModel.initialLoad
            .observeOnMain()
            .subscribeWithErrorLogger { loadingView.isVisible = it }
            .addToDisposables()

        searchViewModel.selectedRepository
            .observeOnMain()
            .subscribeWithErrorLogger {
                mainViewModel.navigateNextStep(Step.DETAIL, bundleOf(
                    Constants.REPOSITORY_ID to it.id,
                    Constants.USER_NAME to it.owner.userName
                ))
            }
            .addToDisposables()
    }

    private fun handleRepositoryItemClick(repository: Repository) {
        searchViewModel.selectRepository(repository)
            .observeOnMain()
            .subscribeWithErrorLogger()
            .addToDisposables()
    }
}