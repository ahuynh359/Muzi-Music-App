package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemSingerNoMoreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SingerAdapter(private val listener: OnSingerClicked) :
    ListAdapter<Singer, SingerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemSingerNoMoreBinding) :
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
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Singer, newItem: Singer): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSingerNoMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnSingerClicked {
        fun onSingerClicked(singer: Singer)
    }

}
