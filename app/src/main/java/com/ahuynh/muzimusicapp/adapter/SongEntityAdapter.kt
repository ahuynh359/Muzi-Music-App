package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.HomeViewHolder
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.ListViewHolder
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.databinding.ItemCircleBigBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundBigBinding
import com.ahuynh.muzimusicapp.databinding.ItemSongRecentBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class SongEntityAdapter(
    private val listener: OnSongEntityClick,
    private val type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var songs: List<SongEntity> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<SongEntity>) {
        songs = data
        notifyDataSetChanged()
    }

    fun getSongs(): List<SongEntity> = songs

    inner class HomeViewHolder(private val binding: ItemCircleBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSongEntityClick(songs[bindingAdapterPosition])
            }

        }

        fun bind(song: SongEntity) {
            binding.imv.loadImage(song.avatar)
            binding.tvDes.text = binding.root.context.getString(
                R.string.song
            )
            binding.tvName.text = song.name
            binding.tvName.isSelected =true
        }
    }

    inner class ListViewHolder(private val binding: ItemSongRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSongEntityClick(songs[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(songs[bindingAdapterPosition])
            }
        }

        fun bind(song: SongEntity) {
            binding.imvSong.loadImage(song.avatar)
            binding.tvNameSong.text = song.name
            binding.tvSinger.text = binding.root.context.getString(
                R.string.song_and_singer_name,
                song.toSong().singers.joinToString(", ") { it.name }
            )
            binding.tvAlbum.text = song.toSong().album.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.type) {
            TYPE_SONG_ENTITY_HOME -> {
                HomeViewHolder(
                    ItemCircleBigBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            TYPE_SONG_ENTITY_RECENTLY -> {
                ListViewHolder(
                    ItemSongRecentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(songs[position])
            is ListViewHolder -> holder.bind(songs[position])
        }
    }

    override fun getItemCount(): Int = songs.size


    interface OnSongEntityClick {
        fun onSongEntityClick(songEntity: SongEntity)
        fun openMenu(songEntity: SongEntity)
    }

    companion object {
        const val TYPE_SONG_ENTITY_HOME = 0
        const val TYPE_SONG_ENTITY_RECENTLY = 1
    }
}
