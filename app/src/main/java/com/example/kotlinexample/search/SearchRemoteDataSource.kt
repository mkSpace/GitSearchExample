package com.example.kotlinexample.search

import com.example.kotlinexample.api.SearchService
import com.example.kotlinexample.network.Response
import io.reactivex.Single
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(private val service: SearchService) {

    fun searchRepositories(query: String, page: Int = 0): Single<Response<List<Repository>>> =
        service.searchRepositories(query, page)
}