package com.example.kotlinexample.search

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "repositories")
data class Repository @Inject constructor(
    @PrimaryKey val id: String,
    val name: String,
    @Embedded val owner: Owner,
    @ColumnInfo(name = "updated_at") @SerializedName("updated_at") val updatedAt: String,
    @ColumnInfo(name = "html_url") @SerializedName("html_url") val htmlUrl: String,
    @ColumnInfo(name = "stargazers_count") @SerializedName("stargazers_count") val starCount: Int,
    val description: String? = null,
    val language: String? = null,
    @ColumnInfo(name = "added_time") val addedTime: Long? = null
) {
    data class Owner(
        @ColumnInfo(name = "owner_id") val id: String,
        @SerializedName("login") val userName: String,
        @SerializedName("avatar_url") val avatarUrl: String
    )
}
