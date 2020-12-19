package com.example.kotlinexample.user

import com.example.kotlinexample.api.UserService
import com.example.kotlinexample.search.Repository
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val service: UserService) {

    fun loadFollowing(userName: String): Single<List<Repository.Owner>> =
        service.getFollowing(userName)

    fun loadFollowers(userName: String): Single<List<Repository.Owner>> =
        service.getFollowers(userName)
}