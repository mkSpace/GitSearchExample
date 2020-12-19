package com.example.kotlinexample.main

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
import kotlinx.android.synthetic.main.item_main_repository.view.*
import java.text.SimpleDateFormat

class MainAdapter : ListAdapter<Repository, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val PAYLOAD_ADDED_TIME_CHANGED = "payload_added_time_changed"
        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<Repository>() {
                override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                    oldItem == newItem

                override fun getChangePayload(oldItem: Repository, newItem: Repository): Any? {
                    return if (oldItem.addedTime != newItem.addedTime &&
                        oldItem.copy(addedTime = newItem.addedTime) == newItem
                    ) {
                        PAYLOAD_ADDED_TIME_CHANGED
                    } else {
                        super.getChangePayload(oldItem, newItem)
                    }
                }
            }).build()
    }
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(parent.inflate(R.layout.item_main_repository))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SearchViewHolder)?.let { it.bind(getItem(position)) }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach { payload ->
                when (payload) {
                    PAYLOAD_ADDED_TIME_CHANGED ->
                        (holder as? SearchViewHolder)?.bindAddedTime(getItem(position))
                }
            }
        }
    }

    private fun SearchViewHolder.bind(item: Repository) {
        GlideApp.with(itemView.context)
            .load(item.owner.avatarUrl)
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(avatar)
        name.text = if (item.name.isNotBlank()) item.name else "No language"
        language.text = item.language
        bindAddedTime(item)
    }

    private fun SearchViewHolder.bindAddedTime(item: Repository) {
        item.addedTime?.let {
            addedTime.text =
                SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
                    .format(it)
        }
    }

    private class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.mainRepositoryAvatar
        val name: TextView = itemView.mainRepositoryName
        val language: TextView = itemView.mainRepositoryLanguage
        val addedTime: TextView = itemView.mainRepositoryAddedTime
    }
}