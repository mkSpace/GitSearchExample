package com.example.kotlinexample.api

import com.example.kotlinexample.network.Response
import com.example.kotlinexample.search.Repository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 0
    ): Single<Response<List<Repository>>>
}