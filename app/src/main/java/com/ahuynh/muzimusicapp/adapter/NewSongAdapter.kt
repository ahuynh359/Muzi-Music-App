package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class NewSongAdapter(
    private val listener: NewSongClicked,
) : RecyclerView.Adapter<NewSongAdapter.SongViewHolder>() {


    private var songs: List<Song> = arrayListOf()

    class SongViewHolder(val itemBinding: ItemSongBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    fun getSongs(): List<Song> = songs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: SongViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val song = songs[position]

        holder.itemBinding.apply {

            imvSong.loadImage(song.avatar)
            tvNameSong.text = song.name
            tvSinger.text = song.singers.joinToString(", ") { it.name }

        }

        holder.itemView.setOnClickListener {
            listener.onSongClick(song)
        }

        holder.itemBinding.btnMore.setOnClickListener {
            listener.onOpenMenu(song, position)
        }
    }

    override fun getItemCount(): Int = songs.size

    interface NewSongClicked {
        fun onSongClick(song: Song)
        fun onOpenMenu(song: Song, position: Int)
    }
}