package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.databinding.ItemMenuBinding

class MenuAdapter(private val listener: OnItemMenuAdapterClicked) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var menuItems: List<ItemMenu> = emptyList()

    fun submitList(data: List<ItemMenu>) {
        menuItems = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onMenuClicked(menuItems[bindingAdapterPosition])
            }
        }

        fun bind(menu: ItemMenu) {
            binding.tvName.text = menu.title
            binding.imvMenu.setImageResource(menu.drawableRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    interface OnItemMenuAdapterClicked {
        fun onMenuClicked(menu: ItemMenu)
    }
}