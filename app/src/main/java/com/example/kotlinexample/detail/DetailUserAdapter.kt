package com.example.kotlinexample.detail

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexample.R
import com.example.kotlinexample.extensions.inflate
import com.example.kotlinexample.search.Repository
import com.mashup.image.GlideApp
import kotlinx.android.synthetic.main.item_detail_users_user.view.*

class DetailUserAdapter : ListAdapter<Repository.Owner, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<Repository.Owner>() {
                override fun areItemsTheSame(
                    oldItem: Repository.Owner,
                    newItem: Repository.Owner
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Repository.Owner,
                    newItem: Repository.Owner
                ): Boolean = oldItem.id == newItem.id

            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder(parent.inflate(R.layout.item_detail_users_user, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as? UserViewHolder)?.apply {
            GlideApp.with(itemView)
                .load(item.avatarUrl)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_account_circle_black_24dp)
                .into(avatar)
            userName.text = item.userName
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.userAvatar
        val userName: TextView = itemView.userName
    }

}