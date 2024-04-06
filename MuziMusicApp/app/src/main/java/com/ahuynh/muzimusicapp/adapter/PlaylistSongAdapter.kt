package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.ahuynh.muzimusicapp.data.model.Song
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PlaylistSongAdapter(private val listener: OnPlaylistSongClicked, private val songList: List<Song>) :
    RecyclerView.Adapter<PlaylistSongAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onPlaylistSongClicked(songList[layoutPosition])
            }
        }

        fun bind(song: Song) {
            Glide.with(binding.imvSong.context).load(song.image).centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.note)
                .into(binding.imvSong)
            binding.tvNameSong.text = song.name
            binding.tvSinger.text = song.singer
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])
    }
}

interface OnPlaylistSongClicked {
    fun onPlaylistSongClicked(song: Song)
}