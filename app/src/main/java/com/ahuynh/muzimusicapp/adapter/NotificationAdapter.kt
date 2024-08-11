package com.ahuynh.muzimusicapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.databinding.ItemNotificationBinding

class NotificationAdapter(private val listener: OnNotificationClicked) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notifications: List<Notification> = emptyList()

    fun submitList(data: List<Notification>) {
        notifications = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onNotificationClicked(notifications[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.openMenu(notifications[bindingAdapterPosition])
            }
        }

        fun bind(notification: Notification) {
            binding.apply {
                imvAvatar.setImageResource(R.drawable.ic_logo)
                tvTitle.text = notification.title
                tvContent.text = notification.content
                tvTime.text = notification.time

                val color = if (notification.status == "NOT_READ") Color.RED else Color.GREEN
                circle.setTextColor(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    interface OnNotificationClicked {
        fun onNotificationClicked(notification: Notification)
        fun openMenu(notification: Notification)
    }
}