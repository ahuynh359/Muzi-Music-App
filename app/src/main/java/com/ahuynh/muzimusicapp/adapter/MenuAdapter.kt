package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.databinding.ItemMenuBinding

class MenuAdapter(private val listener: OnItemMenuAdapterClicked) :
    ListAdapter<ItemMenu, MenuAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onMenuClicked(currentList[layoutPosition])
            }


        }

        fun bind(menu: ItemMenu) {

            binding.tvName.text = menu.title
            binding.imvMenu.setImageResource(menu.drawableRes)

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<ItemMenu>() {
        override fun areItemsTheSame(oldItem: ItemMenu, newItem: ItemMenu): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: ItemMenu, newItem: ItemMenu): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnItemMenuAdapterClicked {
        fun onMenuClicked(menu: ItemMenu)

    }

}