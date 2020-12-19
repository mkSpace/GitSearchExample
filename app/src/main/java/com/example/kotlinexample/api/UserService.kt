package com.example.kotlinexample.api

import com.example.kotlinexample.search.Repository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userName}/following")
    fun getFollowing(@Path("userName") userName: String): Single<List<Repository.Owner>>

    @GET("users/{userName}/followers")
    fun getFollowers(@Path("userName") userName: String): Single<List<Repository.Owner>>

}