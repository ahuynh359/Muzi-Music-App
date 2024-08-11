package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.databinding.ItemLyricsBinding

class LyricAdapter(
    private var lyrics: List<Lyric>,
    private val context: Context,
    private val listener: LyricsClickListener
) : RecyclerView.Adapter<LyricAdapter.LyricViewHolder>() {

    private var current: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newLyrics: List<Lyric>) {
        lyrics = newLyrics
        notifyDataSetChanged()
    }

    fun currentLine(position: Int) {
        if (position != current && position in 0 until itemCount) {
            notifyItemChanged(current)
            current = position
            notifyItemChanged(current)
        }
    }

    fun resetCurrent() {
        current = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewHolder {
        val binding = ItemLyricsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LyricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LyricViewHolder, position: Int) {
        val line = lyrics[position]
        holder.bind(line, position)
    }

    override fun getItemCount(): Int = lyrics.size

    inner class LyricViewHolder(private val binding: ItemLyricsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(line: Lyric, position: Int) {
            binding.tvLyric.text = line.text
            val colorResId = if (current == position) R.color.white else R.color.black
            binding.tvLyric.setTextColor(ContextCompat.getColor(context, colorResId))

            itemView.setOnClickListener {
                listener.onLineLyricsClick(line)
            }
        }
    }

    interface LyricsClickListener {
        fun onLineLyricsClick(line: Lyric)
    }
}