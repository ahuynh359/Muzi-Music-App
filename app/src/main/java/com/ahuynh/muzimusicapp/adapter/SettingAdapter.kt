package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.SettingItem
import com.ahuynh.muzimusicapp.databinding.ItemSettingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SettingAdapter(private val listener: OnSettingAdapterClicked) :
    ListAdapter<SettingItem, SettingAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSettingClicked(currentList[layoutPosition])
            }


        }

        fun bind(setting: SettingItem) {

            binding.tvName.text = setting.name.name

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<SettingItem>() {
        override fun areItemsTheSame(oldItem: SettingItem, newItem: SettingItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SettingItem, newItem: SettingItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
    interface OnSettingAdapterClicked {
        fun onSettingClicked(setting: SettingItem)

    }

}

