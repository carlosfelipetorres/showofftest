package com.carlostorres.showofftest.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlostorres.client.domain.models.Post
import com.carlostorres.showofftest.R
import com.carlostorres.showofftest.databinding.ItemPostBinding


/**
 * List item adapter
 */
class PostsAdapter(val name: String) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private val list = mutableListOf<Post>()
    private var context: Context? = null

    /**
     * Replace all the elements on the list and fill with the new one received as parameter
     */
    fun updateList(newList: List<Post>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = DataBindingUtil.inflate<ItemPostBinding>(inflater, R.layout.item_post, parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            post.name = name
            binding.item = post
            binding.executePendingBindings()
        }
    }
}