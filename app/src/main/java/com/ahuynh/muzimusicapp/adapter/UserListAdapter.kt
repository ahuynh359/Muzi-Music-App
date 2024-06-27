package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.SettingItem
import com.ahuynh.muzimusicapp.data.model.UserList
import com.ahuynh.muzimusicapp.data.model.UserListName
import com.ahuynh.muzimusicapp.databinding.ItemSettingBinding
import com.ahuynh.muzimusicapp.databinding.ItemUserListBinding

class UserListAdapter(private val listener: OnUserListAdapterClicked) :
    ListAdapter<UserList, UserListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onUserListAdapterClicked(currentList[layoutPosition])
            }


        }

        fun bind(str: UserList) {
            var s : String = ""
            when (str.name) {
                UserListName.PLAYLIST -> s = "Playlist"
                UserListName.LOVESONG ->  s = "Love Song"
                UserListName.LOVESINGER -> s = "Love Singer"
            }
            binding.tvName.text = s
            binding.tvUser.text = "User"

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<UserList>() {
        override fun areItemsTheSame(oldItem: UserList, newItem: UserList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserList, newItem: UserList): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnUserListAdapterClicked {
        fun onUserListAdapterClicked(str: UserList)

    }

}

