package com.example.mapin.ui.main_post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapin.R
import com.example.mapin.databinding.ContentItemBinding
import com.example.mapin.network.model.Post


class MainPostAdapter : ListAdapter<Post, MainPostAdapter.MyViewHolder>(diffUtil) {
    class MyViewHolder(private val binding: ContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            itemView.setOnClickListener{
                val id = bundleOf("id" to item.id.toString())
                itemView.findNavController().navigate(R.id.action_mainPostFragment_to_mainPostDetailFragment, id)
            }
            binding.itemImageView.apply {
                Glide.with(this.context).load(item.image).into(this)
            }
            binding.contentTitle.text = item.title
            binding.locationText.text = item.dong
            binding.timeText.text = item.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

}
