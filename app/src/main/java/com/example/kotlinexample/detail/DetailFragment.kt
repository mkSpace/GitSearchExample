package com.example.kotlinexample.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kotlinexample.BaseFragment
import com.example.kotlinexample.Constants
import com.example.kotlinexample.Injection
import com.example.kotlinexample.R
import com.example.kotlinexample.rx.observeOnMain
import com.example.kotlinexample.rx.subscribeWithErrorLogger
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {

    private val detailViewModel by viewModels<DetailViewModel> {
        Injection.provideDetailViewModelFactory(requireContext(), repositoryId, userName)
    }

    private val repositoryId by lazy { arguments?.getString(Constants.REPOSITORY_ID) ?: "" }
    private val userName by lazy { arguments?.getString(Constants.USER_NAME) ?: "" }

    private lateinit var adapter: DetailAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = DetailAdapter(::handleUrlClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailRecyclerView.adapter = adapter
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel.ownerName
            .observeOnMain()
            .subscribeWithErrorLogger { toolbar.title = it }
            .addToDisposables()

        detailViewModel.items
            .observeOnMain()
            .subscribeWithErrorLogger (adapter::submitList)
            .addToDisposables()
    }

    private fun handleUrlClick(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}