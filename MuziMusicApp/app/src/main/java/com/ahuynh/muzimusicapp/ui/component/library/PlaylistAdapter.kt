package com.ahuynh.muzimusicapp.ui.component.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.ItemPlaylistBinding
import com.ahuynh.muzimusicapp.model.Playlist
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PlaylistAdapter(private val listener: OnPlaylistClicked) :
    ListAdapter<Playlist, PlaylistAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onPlaylistClicked(currentList[layoutPosition])
            }
        }

        fun bind(playlist: Playlist) {
            Glide
                .with(binding.imvSong.context)
                .load(playlist.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.note)
                .into(binding.imvSong);
            binding.tvPlaylist.text = playlist.name
            binding.tvUser.text = "Anh Huynh"

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}


interface OnPlaylistClicked {
    fun onPlaylistClicked(playlist: Playlist)
}