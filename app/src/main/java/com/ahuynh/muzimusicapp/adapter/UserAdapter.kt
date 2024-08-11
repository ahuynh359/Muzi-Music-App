package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class UserAdapter(private val listener: OnUserClicked) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users: List<User> = emptyList()

    fun submitList(data: List<User>) {
        users = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onUserClicked(users[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreClicked(users[bindingAdapterPosition])
            }
        }

        fun bind(user: User) {
            binding.imv.loadImage(user.avatar)
            binding.tvName.text = user.username
            binding.tvDes.text = user.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    interface OnUserClicked {
        fun onUserClicked(user: User)
        fun onMoreClicked(user: User)
    }
}