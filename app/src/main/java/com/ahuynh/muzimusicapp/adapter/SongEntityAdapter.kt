package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemCircleRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SongEntityAdapter(private val listener: OnSongEntityClick) :
    ListAdapter<SongEntity, SongEntityAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCircleRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSongEntityClick(currentList[layoutPosition])
            }

        }

        fun bind(song: SongEntity) {
            binding.imvSinger.loadImage(song.avatar)
            binding.tvName.text = song.name



        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<SongEntity>() {
        override fun areItemsTheSame(oldItem: SongEntity, newItem: SongEntity): Boolean {
            return oldItem.songId == newItem.songId
        }

        override fun areContentsTheSame(oldItem: SongEntity, newItem: SongEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCircleRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnSongEntityClick {
        fun onSongEntityClick(songEntity: SongEntity)
    }

}

