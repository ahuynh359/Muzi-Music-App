package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundBigBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage


class AlbumAdapter(
    private val listener: OnAlbumClicked,
    private val viewType: AlbumViewType
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var albums: List<Album> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Album>) {
        albums = data
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: ItemRoundBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onAlbumClicked(albums[bindingAdapterPosition])
            }
        }

        fun bind(album: Album) {
            binding.apply {
                imv.loadImage(album.avatar)
                tvName.text = album.name
                tvName.isSelected = true
                tvDes.text = binding.root.context.getString(R.string.album)
            }
        }
    }

    inner class ListViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onAlbumClicked(albums[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreItemAlbumClicked(albums[bindingAdapterPosition])
            }
        }

        fun bind(album: Album) {
            binding.apply {
                imv.loadImage(album.avatar)
                tvName.text = album.name
                tvName.isSelected = true
                tvDes.text = binding.root.context.getString(R.string.album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            AlbumViewType.HOME -> {
                val binding = ItemRoundBigBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HomeViewHolder(binding)
            }

            AlbumViewType.LIST -> {
                val binding = ItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ListViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(albums[position])
            is ListViewHolder -> holder.bind(albums[position])
        }
    }

    override fun getItemCount(): Int = albums.size

    interface OnAlbumClicked {
        fun onAlbumClicked(album: Album)
        fun onMoreItemAlbumClicked(album: Album)
    }
    enum class AlbumViewType {
        HOME, LIST
    }
}