package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.ItemCircleRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemSingerBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

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
           binding.imvSinger.loadImage(singer.avatar)
            binding.tvName.text = singer.name
        }
    }

    inner class ListViewHolder(private val binding: ItemSingerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSingerClicked(currentList[layoutPosition])
            }
        }

        fun bind(singer: Singer) {
            binding.imvSinger.loadImage(singer.avatar)
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
                val binding = ItemSingerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val singer = currentList[position]
        when (holder) {
            is HomeViewHolder -> holder.bind(singer)
            is ListViewHolder -> holder.bind(singer)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnSingerClicked {
        fun onSingerClicked(singer: Singer)
    }
}