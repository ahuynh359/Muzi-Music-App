package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.HomeViewHolder
import com.ahuynh.muzimusicapp.adapter.SingerAdapter.ListViewHolder
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.databinding.ItemCircleBigBinding
import com.ahuynh.muzimusicapp.databinding.ItemSongChartBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class ChartAdapter(private val listener: OnChartClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var songs: List<Song> = arrayListOf()

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
            binding.imvSong.loadImage(song.avatar)
            binding.tvNameSong.text = song.name
            binding.tvSinger.text = song.singers.joinToString(", ") { it.name }
            binding.tvIndex.text = (layoutPosition + 1).toString()
            if (bindingAdapterPosition == 0) {
                binding.tvIndex.setTextColor(Color.rgb(47, 148, 240))
            } else if (bindingAdapterPosition == 1) {
                binding.tvIndex.setTextColor(Color.rgb(56, 202, 147))
            } else if (bindingAdapterPosition == 2) {
                binding.tvIndex.setTextColor(Color.rgb(227, 121, 68))
            } else
                binding.tvIndex.setTextColor(Color.WHITE)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemSongChartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(songs[position])
        }
    }


    override fun getItemCount(): Int {
        return songs.size
    }

    interface OnChartClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song: Song)
    }

}

