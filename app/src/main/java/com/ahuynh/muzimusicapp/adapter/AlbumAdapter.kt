package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.ItemAlbumBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

enum class AlbumViewType {
    HOME, LIST
}

class AlbumAdapter(
    private val listener: OnAlbumClicked,
    private val viewType: AlbumViewType
) : ListAdapter<Album, RecyclerView.ViewHolder>(DiffCallback()) {

    inner class HomeViewHolder(private val binding: ItemRoundRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onAlbumClicked(currentList[layoutPosition])
            }
        }

        fun bind(album: Album) {
            Glide
                .with(binding.imv.context)
                .load(album.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imv)
            binding.tvName.text = album.name
        }
    }

    inner class ListViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onAlbumClicked(currentList[layoutPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreItemAlbumClicked(currentList[layoutPosition])
            }
        }

        fun bind(album: Album) {
            Glide
                .with(binding.imvAlbum.context)
                .load(album.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAlbum)
            binding.tvAlbumName.text = album.name
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            AlbumViewType.HOME -> {
                val binding =
                    ItemRoundRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeViewHolder(binding)
            }
            AlbumViewType.LIST -> {
                val binding =
                    ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = currentList[position]
        when (holder) {
            is HomeViewHolder -> holder.bind(album)
            is ListViewHolder -> holder.bind(album)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnAlbumClicked {
        fun onAlbumClicked(album: Album)
        fun onMoreItemAlbumClicked(album: Album)
    }
}