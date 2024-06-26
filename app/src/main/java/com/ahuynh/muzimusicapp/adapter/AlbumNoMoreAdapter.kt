package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.ItemAlbumBinding
import com.ahuynh.muzimusicapp.databinding.ItemAlbumNoMoreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumNoMoreAdapter(private val listener: OnAlbumAdapterClicked) :
    ListAdapter<Album, AlbumNoMoreAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemAlbumNoMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onAlbumClicked(currentList[layoutPosition])
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
            //binding.tvAlbumName.text = album.description

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlbumNoMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
    interface OnAlbumAdapterClicked {
        fun onAlbumClicked(album: Album)

    }

}


