package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song

class VerticalSongAdapter(
    private val lists: List<List<Song>>,
    private val listener: SongAdapter.OnSongClicked
) : RecyclerView.Adapter<VerticalSongAdapter.VerticalListViewHolder>() {

    class VerticalListViewHolder(val recyclerView: RecyclerView) :
        RecyclerView.ViewHolder(recyclerView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalListViewHolder {
        val recyclerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vertical_song, parent, false) as RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        return VerticalListViewHolder(recyclerView)
    }

    override fun onBindViewHolder(holder: VerticalListViewHolder, position: Int) {
        val songList = lists[position]
        val songAdapter = SongAdapter(listener)
        holder.recyclerView.adapter = songAdapter
        songAdapter.submitList(songList)
    }

    override fun getItemCount(): Int = lists.size


}