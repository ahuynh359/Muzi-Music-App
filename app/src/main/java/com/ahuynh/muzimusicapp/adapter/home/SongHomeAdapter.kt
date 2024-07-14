package com.ahuynh.muzimusicapp.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemCircleRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SongHomeAdapter(private val currentList: List<Song>, private val listener: OnSongHomeClick) :
    RecyclerView.Adapter<SongHomeAdapter.ViewHolder>() {

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
                .into(binding.imvSong)
            binding.tvNameSong.text = song.name
            binding.tvSinger.text = song.singers.joinToString(", ") { it.name }


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

    interface OnSongHomeClick {
        fun onSongClicked(song: Song)
        fun openMenu(song: Song)
    }


}