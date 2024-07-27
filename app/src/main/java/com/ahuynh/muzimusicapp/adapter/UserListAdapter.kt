package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.UserList
import com.ahuynh.muzimusicapp.databinding.ItemUserListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

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

            binding.tvName.text = str.title
            binding.tvUser.text = "User"
            Glide
                .with(binding.imvItem.context)
                .load(str.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvItem);


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

