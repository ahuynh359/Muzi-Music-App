package com.ahuynh.muzimusicapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.databinding.ItemNotificationBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class NotificationAdapter(private val listener: OnNotificationClicked) :
    ListAdapter<Notification, NotificationAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onNotificationClicked(currentList[layoutPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(currentList[layoutPosition])
            }
        }
        fun bind(notification: Notification) {
            binding.imvAvatar.setImageResource(R.drawable.ic_spotify_white)

            binding.tvTitle.text = notification.title
            binding.tvContent.text = notification.content
            binding.tvTime.text = notification.time
            if(notification.status == "NOT_READ")
                binding.circle.setTextColor(Color.RED)
            else
                binding.circle.setTextColor(Color.GREEN)



        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    interface OnNotificationClicked {
        fun onNotificationClicked(notification: Notification)
        fun openMenu(notification : Notification)
    }

}
