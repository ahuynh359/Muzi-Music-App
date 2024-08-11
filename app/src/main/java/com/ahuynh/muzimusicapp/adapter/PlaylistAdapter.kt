package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class PlaylistAdapter(
    private val listener: OnPlaylistClicked,
    private val showMoreButton: Boolean = true
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    private var playlists: List<Playlist> = emptyList()

    fun submitList(data: List<Playlist>) {
        playlists = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onPlaylistClicked(playlists[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreItemClicked(playlists[bindingAdapterPosition])
            }
        }

        fun bind(playlist: Playlist) {
            binding.apply {
                imv.loadImage(playlist.avatar)
                tvName.text = playlist.name
                btnMore.visibility = if (showMoreButton) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    override fun getItemCount(): Int = playlists.size

    interface OnPlaylistClicked {
        fun onPlaylistClicked(playlist: Playlist)
        fun onMoreItemClicked(playlist: Playlist)
    }
}