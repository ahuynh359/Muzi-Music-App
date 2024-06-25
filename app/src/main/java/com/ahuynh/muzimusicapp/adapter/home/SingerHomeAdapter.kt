package com.ahuynh.muzimusicapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemCircleRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SingerHomeAdapter(private val listener: OnSingerHomeClicked) :
    ListAdapter<Singer, SingerHomeAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCircleRecentlyBinding) :
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
                .placeholder(R.drawable.note)
                .into(binding.imv)
            binding.tvName.text = singer.name


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCircleRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnSingerHomeClicked {
        fun onSingerClicked(singer: Singer)
    }

}