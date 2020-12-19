package com.example.kotlinexample.user

import com.example.kotlinexample.search.Repository
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val remote: UserRemoteDataSource) {

    fun loadFollowing(userName: String): Single<List<Repository.Owner>> =
        remote.loadFollowing(userName)

    fun loadFollowers(userName: String): Single<List<Repository.Owner>> =
        remote.loadFollowers(userName)
}