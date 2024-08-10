package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage


class SongAdapter(private val listener: OnSongClicked) :
    ListAdapter<Song, SongAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSongClicked(currentList[layoutPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(currentList[layoutPosition])
            }
        }
        fun bind(song: Song) {
            binding.imv.loadImage(song.avatar)
            binding.tvName.text = song.name
            binding.tvDes.text = song.singers.joinToString(", ") { it.name }


        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    interface OnSongClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song : Song)
    }

}

