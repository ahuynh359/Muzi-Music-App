package com.ahuynh.muzimusicapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemSongBinding
import com.ahuynh.muzimusicapp.databinding.ItemSongChartBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ChartAdapter(private val listener: OnChartClicked) :
    ListAdapter<Song, ChartAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemSongChartBinding) :
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
            binding.imvSong.loadImage(song.avatar)
            binding.tvNameSong.text = song.name
            binding.tvSinger.text = song.singers.joinToString(", ") { it.name }
            binding.tvIndex.text = (layoutPosition + 1).toString()
            if(layoutPosition == 0){
                binding.tvIndex.setTextColor( Color.rgb(47,148,240),)
            } else if(layoutPosition == 1){
                binding.tvIndex.setTextColor( Color.rgb(56,202,147))
            } else if(layoutPosition == 2){
                binding.tvIndex.setTextColor(Color.rgb(227,121,68))
            }else
                binding.tvIndex.setTextColor(Color.WHITE)


        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnChartClicked {
        fun onSongClicked(song: Song)
        fun openMenu(song : Song)
    }

}

