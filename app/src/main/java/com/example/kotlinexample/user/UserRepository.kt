package com.example.kotlinexample.user

import com.example.kotlinexample.search.Repository
import io.reactivex.Single

class UserRepository(private val remote: UserRemoteDataSource) {

    fun loadFollowing(userName: String): Single<List<Repository.Owner>> =
        remote.loadFollowing(userName)

    fun loadFollowers(userName: String): Single<List<Repository.Owner>> =
        remote.loadFollowers(userName)
}