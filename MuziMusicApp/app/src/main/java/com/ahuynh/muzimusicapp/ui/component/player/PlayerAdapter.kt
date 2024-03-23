package com.ahuynh.muzimusicapp.ui.component.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.databinding.ItemLyricsBinding
import com.ahuynh.muzimusicapp.model.Lyric

class PlayerAdapter(
    private val listener: OnLyricClicked,
    private val lyricsList: ArrayList<Lyric>
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemLyricsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onLyricClicked(lyricsList[layoutPosition])
            }
        }

        fun bind(lyric: Lyric) {
            binding.tvLyric.text = lyric.text

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLyricsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lyricsList[position])
    }

    override fun getItemCount(): Int {
        return lyricsList.size
    }


}


interface OnLyricClicked {
    fun onLyricClicked(lyrics: Lyric)
}