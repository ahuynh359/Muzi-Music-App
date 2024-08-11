package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemSongChartBinding
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class ChartAdapter(private val listener: OnChartClicked) :
    RecyclerView.Adapter<ChartAdapter.ViewHolder>() {

    private var songs: List<Song> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSongChartBinding) :
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
            binding.apply {
                imvSong.loadImage(song.avatar)
                tvNameSong.text = song.name
                tvNameSong.isSelected = true
                tvSinger.text = song.singers.joinToString(", ") { it.name }
                tvIndex.text = (bindingAdapterPosition + 1).toString()
                tvIndex.setTextColor(
                    when (bindingAdapterPosition) {
                        0 -> Constants.colorsTopSong[0]
                        1 -> Constants.colorsTopSong[1]
                        2 -> Constants.colorsTopSong[2]
                        else -> Color.WHITE
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongChartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    interface OnChartClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song: Song)
    }
}