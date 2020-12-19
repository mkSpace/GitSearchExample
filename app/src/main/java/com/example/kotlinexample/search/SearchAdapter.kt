package com.example.kotlinexample.search

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
import com.mashup.image.GlideApp
import kotlinx.android.synthetic.main.item_repository.view.*

class SearchAdapter(
    private val listener: OnClickListener?
) : ListAdapter<Repository, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<Repository>() {
                override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem == newItem
            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(parent.inflate(R.layout.item_repository))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SearchViewHolder)?.let { it.bind(getItem(position)) }
    }

    private fun SearchViewHolder.bind(item: Repository) {
        GlideApp.with(itemView.context)
            .load(item.owner.avatarUrl)
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(avatar)
        name.text = if (item.name.isNotBlank()) item.name else "No language"
        language.text = item.language
        itemView.setOnClickListener { listener?.onClickRepository(item) }
    }

    private class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.repositoryAvatar
        val name: TextView = itemView.repositoryName
        val language: TextView = itemView.repositoryLanguage
    }

    interface OnClickListener {
        fun onClickRepository(repository: Repository)
    }
}