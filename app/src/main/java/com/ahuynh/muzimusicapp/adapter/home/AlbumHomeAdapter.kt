package com.ahuynh.muzimusicapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.databinding.ItemAlbumBinding
import com.ahuynh.muzimusicapp.databinding.ItemRoundRecentlyBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class AlbumHomeAdapter(private val listener: OnAlbumHomeAdapterClicked) :
    ListAdapter<Album, AlbumHomeAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemRoundRecentlyBinding) :
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
                .placeholder(R.drawable.note)
                .into(binding.imv)
            binding.tvName.text = album.name
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
            ItemRoundRecentlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnAlbumHomeAdapterClicked {
        fun onAlbumClicked(album: Album)

    }

}


