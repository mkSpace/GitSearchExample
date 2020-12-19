package com.example.kotlinexample.user

import com.example.kotlinexample.api.UserService
import com.example.kotlinexample.search.Repository
import io.reactivex.Single

class UserRemoteDataSource(private val service: UserService) {

    fun loadFollowing(userName: String): Single<List<Repository.Owner>> =
        service.getFollowing(userName)

    fun loadFollowers(userName: String): Single<List<Repository.Owner>> =
        service.getFollowers(userName)
}