package com.example.mapin.ui.main_content

import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mapin.R
import com.example.mapin.data.model.AdItem
import com.example.mapin.databinding.ContentItemBinding
import com.example.mapin.databinding.ContentMainBinding
import com.example.mapin.databinding.ItemRvAdBinding


class AdAdapter : ListAdapter<AdItem,AdAdapter.MyViewHolder>(diffUtil) {
    class MyViewHolder(private val binding: ItemRvAdBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdItem) {

            binding.imgRv.apply {
                Glide.with(this.context).load(item.image).into(this)
                clipToOutline = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRvAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AdItem>() {
            override fun areItemsTheSame(oldItem: AdItem, newItem: AdItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AdItem, newItem: AdItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
