package com.example.kotlinexample.detail

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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
import kotlinx.android.synthetic.main.item_detail_information.view.*
import kotlinx.android.synthetic.main.item_detail_profile.view.*
import kotlinx.android.synthetic.main.item_detail_users.view.*

class DetailAdapter(
    private val listener : OnClickListener? = null
) : ListAdapter<DetailAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {

        private const val VIEW_TYPE_PROFILE = R.layout.item_detail_profile
        private const val VIEW_TYPE_INFORMATION = R.layout.item_detail_information
        private const val VIEW_TYPE_USERS = R.layout.item_detail_users

        private val DIFF_CALLBACK =
            AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<DetailAdapterItem>() {
                override fun areItemsTheSame(
                    oldItem: DetailAdapterItem,
                    newItem: DetailAdapterItem
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: DetailAdapterItem,
                    newItem: DetailAdapterItem
                ): Boolean = oldItem == newItem
            }).build()
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.hashCode().toLong()

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is DetailAdapterItem.Repository -> VIEW_TYPE_PROFILE
        is DetailAdapterItem.Information -> VIEW_TYPE_INFORMATION
        is DetailAdapterItem.FollowUsers -> VIEW_TYPE_USERS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.inflate(viewType)
        return when (viewType) {
            VIEW_TYPE_PROFILE -> ProfileViewHolder(itemView)
            VIEW_TYPE_INFORMATION -> InformationViewHolder(itemView)
            VIEW_TYPE_USERS -> UsersViewHolder(itemView)
            else -> object : RecyclerView.ViewHolder(itemView) {}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        when {
            item is DetailAdapterItem.Repository && holder is ProfileViewHolder -> holder.bind(item)
            item is DetailAdapterItem.Information && holder is InformationViewHolder -> holder.bind(
                item
            )
            item is DetailAdapterItem.FollowUsers && holder is UsersViewHolder -> holder.bind(item)
        }
    }

    private fun ProfileViewHolder.bind(item: DetailAdapterItem.Repository) {
        GlideApp.with(itemView)
            .load(item.userAvatar)
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(avatar)
        SpannableStringBuilder(item.repoName).apply {
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    listener?.onUrlClick(item.repoUrl)
                }
            }, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }.let {
            name.movementMethod = LinkMovementMethod.getInstance()
            name.setText(it, TextView.BufferType.SPANNABLE)
        }
        starCount.text = itemView.context.resources.getQuantityString(
            R.plurals.plurals_star,
            item.startCount,
            item.startCount.toString()
        )
    }

    private fun InformationViewHolder.bind(item: DetailAdapterItem.Information) {
        icon.setImageDrawable(itemView.context.resources.getDrawable(item.icon, null))
        content.text = item.content
        title.text = item.title
    }

    private fun UsersViewHolder.bind(item: DetailAdapterItem.FollowUsers) {
        title.text =
            itemView.context.getText(if (item.isFollowing) R.string.following else R.string.followers)
        adapter.submitList(item.users)
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.detailUserAvatar
        val name: TextView = itemView.detailRepoName
        val starCount: TextView = itemView.detailRepoStarCount
    }

    class InformationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.detailInformationIcon
        val content: TextView = itemView.detailInformationTitle
        val title: TextView = itemView.detailInformationContent
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val users: RecyclerView = itemView.detailUsers
        val title: TextView = itemView.detailUsersTitle
        val adapter = DetailUserAdapter()

        init {
            users.adapter = adapter
        }
    }

    interface OnClickListener {
        fun onUrlClick(url: String)
    }
}
