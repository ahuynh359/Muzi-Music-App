package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class SongAdapter(private val listener: OnSongClicked) :
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var songs: List<Song> = arrayListOf()

    fun submitList(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onSongClicked(songs[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(songs[bindingAdapterPosition])
            }
        }

        fun bind(song: Song) {
            binding.imv.loadImage(song.avatar)
            binding.tvName.text = song.name
            binding.tvDes.text = song.singers.joinToString(", ") { it.name }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    interface OnSongClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song: Song)
    }
}