package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.ItemCircleRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemSingerNoMoreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

enum class SingerViewType {
    HOME, LIST
}

class SingerAdapter(
    private val listener: OnSingerClicked,
    private val viewType: SingerViewType
) : ListAdapter<Singer, RecyclerView.ViewHolder>(DiffCallback()) {

    inner class HomeViewHolder(private val binding: ItemCircleRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSingerClicked(currentList[layoutPosition])
            }
        }

        fun bind(singer: Singer) {
            Glide
                .with(binding.imv.context)
                .load(singer.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imv)
            binding.tvName.text = singer.name
        }
    }

    inner class LISTViewHolder(private val binding: ItemSingerNoMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSingerClicked(currentList[layoutPosition])
            }
        }

        fun bind(singer: Singer) {
            Glide
                .with(binding.imvSinger.context)
                .load(singer.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvSinger)
            binding.tvSinger.text = singer.name
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Singer>() {
        override fun areItemsTheSame(oldItem: Singer, newItem: Singer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Singer, newItem: Singer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            SingerViewType.HOME -> {
                val binding = ItemCircleRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeViewHolder(binding)
            }
            SingerViewType.LIST -> {
                val binding = ItemSingerNoMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LISTViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val singer = currentList[position]
        when (holder) {
            is HomeViewHolder -> holder.bind(singer)
            is LISTViewHolder -> holder.bind(singer)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnSingerClicked {
        fun onSingerClicked(singer: Singer)
    }
}