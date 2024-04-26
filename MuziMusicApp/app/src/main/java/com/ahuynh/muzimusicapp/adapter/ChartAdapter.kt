package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.ItemSongChartBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ChartAdapter(
    private val listener: OnSongChartClicked,
) : RecyclerView.Adapter<ChartAdapter.SongViewHolder>() {


    private var songs: List<Song> = arrayListOf()

    class SongViewHolder(val itemBinding: ItemSongChartBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Song>) {
        songs = data
        notifyDataSetChanged()
    }

    fun getSongs(): List<Song> = songs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemSongChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(
        holder: SongViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val song = songs[position]

        holder.itemBinding.apply {

            Glide
                .with(imvSong.context)
                .load(song.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.note)
                .into(imvSong)
            tvNameSong.text = song.name
            tvSinger.text = song.singer
            tvIndex.text = (position + 1).toString()

            if(position == 0){
                tvIndex.setTextColor(android.graphics.Color.RED)
            } else if(position == 1){
                tvIndex.setTextColor(android.graphics.Color.GREEN)
            } else if(position == 2){
                tvIndex.setTextColor(android.graphics.Color.YELLOW)
            } else
                tvIndex.setTextColor(android.graphics.Color.WHITE)

        }

        holder.itemView.setOnClickListener {
            listener.onSongClicked(song)
        }

    }

}

interface OnSongChartClicked {
    fun onSongClicked(song: Song)
}