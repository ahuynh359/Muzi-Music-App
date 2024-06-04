package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class NewSongAdapter(private val listener: OnNewSongClicked) :
    ListAdapter<Song, NewSongAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.main.setOnClickListener {
                listener.onSongClicked(currentList[layoutPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(currentList[layoutPosition])
            }
        }
        fun bind(song: Song) {
            Glide
                .with(binding.imvSong.context)
                .load(song.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.note)
                .into(binding.imvSong)
            binding.tvNameSong.text = song.name
            //binding.tvSinger.text = song.singer


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
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnNewSongClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song : Song)
    }

}

