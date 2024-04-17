package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Lyric
import com.ahuynh.muzimusicapp.databinding.ItemLyricsBinding

class LyricAdapter(
    private var lyrics: ArrayList<Lyric>,
    val context: Context,
    val listener: LyricsClickListener
) :
    RecyclerView.Adapter<LyricAdapter.LyricViewHolder>() {

    private var current: Int = -1;

    @SuppressLint("NotifyDataSetChanged")
    fun setData(lyrics: ArrayList<Lyric>) {
        this.lyrics.clear()
        this.lyrics.addAll(lyrics)
        notifyDataSetChanged()
    }

    fun currentLine(position: Int) {
        if (position != current && position >= 0 && position < itemCount) {
            notifyItemChanged(current)
            current = position
            notifyItemChanged(current)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewHolder {
        return LyricViewHolder(
            ItemLyricsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LyricViewHolder, position: Int) {
        val line = lyrics[position]

        holder.itemBinding.apply {
            tvLyric.text = line.text

            if (current == position) {
                tvLyric.setTextColor(ContextCompat.getColor(context, R.color.white))
                tvLyric.typeface = Typeface.DEFAULT_BOLD
            } else {
                tvLyric.setTextColor(ContextCompat.getColor(context, R.color.black))
                tvLyric.typeface = Typeface.DEFAULT
            }
        }

        holder.itemView.setOnClickListener {
            listener.onLineLyricsClick(line)
        }
    }

    override fun getItemCount(): Int = lyrics.size

    inner class LyricViewHolder(val itemBinding: ItemLyricsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}

interface LyricsClickListener {
    fun onLineLyricsClick(line: Lyric)
}