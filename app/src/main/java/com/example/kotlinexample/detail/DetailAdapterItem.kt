package com.example.kotlinexample.detail

import com.example.kotlinexample.R
import com.example.kotlinexample.search.Repository
import com.example.kotlinexample.search.Repository.Owner

sealed class DetailAdapterItem(val id: String) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = id.hashCode()

    data class Repository(
        val repoId: String,
        val userAvatar: String,
        val repoName: String,
        val repoUrl: String,
        val startCount: Int
    ) : DetailAdapterItem(repoId)

    data class Information(
        val icon: Int,
        val title: String,
        val content: String
    ) : DetailAdapterItem(title)

    data class FollowUsers(
        val users: List<Owner>,
        val isFollowing: Boolean
    ) : DetailAdapterItem("${if (isFollowing) "following" else "follower"}-users")
}

fun Repository.toDetailAdapterItems(): List<DetailAdapterItem> =
    listOfNotNull(
        DetailAdapterItem.Repository(
            repoId = id,
            userAvatar = owner.avatarUrl,
            repoName = name,
            repoUrl = htmlUrl,
            startCount = starCount
        ),
        if (!description.isNullOrBlank()) {
            DetailAdapterItem.Information(
                icon = R.drawable.ic_info_24dp,
                title = "Description",
                content = description
            )
        } else null,
        if (!language.isNullOrBlank()) {
            DetailAdapterItem.Information(
                icon = R.drawable.ic_language_black_24dp,
                title = "Language",
                content = language
            )
        } else null,
        DetailAdapterItem.Information(
            icon = R.drawable.ic_update_black_24dp,
            title = "Last Updated",
            content = updatedAt
        )
    )

fun List<Owner>.toDetailAdapterItem(isFollowing: Boolean): DetailAdapterItem =
    DetailAdapterItem.FollowUsers(this, isFollowing)