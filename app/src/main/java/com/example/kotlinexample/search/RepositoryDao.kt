package com.example.kotlinexample.search

import androidx.room.Dao
import androidx.room.Query
import com.example.kotlinexample.BaseDao
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RepositoryDao : BaseDao<Repository> {

    @Query("SELECT * FROM repositories WHERE id = :id")
    fun getRepositorySingle(id: String): Single<Repository>

    @Query("SELECT * FROM repositories ORDER BY added_time DESC")
    fun getRepositoriesFlowable(): Flowable<List<Repository>>
}