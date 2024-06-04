package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.databinding.ItemSongAddBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SongAddAdapter(
    private val currentPlaylist  : Playlist,
    private val listener: OnSongAddAdapter,
) : RecyclerView.Adapter<SongAddAdapter.SongViewHolder>() {


    private var songOlds: List<SongOld> = arrayListOf()
    private lateinit var checkboxStates : BooleanArray
    class SongViewHolder(val itemBinding: ItemSongAddBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SongOld>) {
        songOlds = data
        checkboxStates = BooleanArray(songOlds.size) { false }
        notifyDataSetChanged()
    }

    fun getSongs(): List<SongOld> = songOlds
    fun checkboxStates() = checkboxStates

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemSongAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return songOlds.size
    }

    override fun onBindViewHolder(
        holder: SongViewHolder,
        position: Int
    ) {
        val song = songOlds[position]


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


            cmbCheck.setOnCheckedChangeListener { _, isChecked ->
                checkboxStates[position] = isChecked
            }
            val songListOfPlaylist = currentPlaylist.songs
            if(songListOfPlaylist != null && songListOfPlaylist.indexOf(song.id) != -1){
                cmbCheck.isChecked = true
            } else
                cmbCheck.isChecked = false
        }


    }

}


interface OnSongAddAdapter {
}
