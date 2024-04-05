package com.ahuynh.muzimusicapp.ui.component.player

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.databinding.ItemLyricsBinding
import com.ahuynh.muzimusicapp.model.Lyric

class PlayerAdapter(private val listener: OnLyricsClicked) :
    ListAdapter<Lyric, PlayerAdapter.ViewHolder>(DiffCallback()) {
    private var current = -1

    inner class ViewHolder(private val binding: ItemLyricsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onLyricsClicked(currentList[layoutPosition])
            }
        }

        fun bind(lyric: Lyric) {
            binding.tvLyric.text = lyric.text

            if (current == layoutPosition) {
                binding.tvLyric.setTextColor(Color.WHITE);
            } else
                binding.tvLyric.setTextColor(Color.BLACK);
        }

    }

    fun currentLine(position: Int) {
        if (position != current && position >= 0 && position < itemCount) {
            notifyItemChanged(current)
            current = position
            notifyItemChanged(current)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Lyric>() {
        override fun areItemsTheSame(oldItem: Lyric, newItem: Lyric): Boolean {
            return oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: Lyric, newItem: Lyric): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLyricsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}


interface OnLyricsClicked {
    fun onLyricsClicked(lyric: Lyric)
}