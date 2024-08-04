package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.databinding.ItemCommentBinding
import com.ahuynh.muzimusicapp.databinding.ItemCommentReplyBinding
import com.ahuynh.muzimusicapp.databinding.ItemCommentWithReplyBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CommentAdapter(private val listener: OnCommentClicked,private val hideBtnReply: Boolean = false) :
    ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemCommentWithReplyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {


            binding.btnMore.setOnClickListener {
                listener.openMenu(currentList[layoutPosition])
            }

            binding.btnReply.setOnClickListener {
                listener.replyComment(currentList[layoutPosition])
            }
        }

        fun bind(comment: Comment) {
            binding.imvAvatar.loadImage(comment.user.avatar)
            binding.tvName.text = comment.user.username
            binding.tvContent.text = comment.content
            binding.tvTime.text = comment.time
            if(hideBtnReply){
                binding.btnReply.visibility = View.GONE
            }
            
            val replyAdapter = CommentAdapter(listener,true)
            binding.rcyCommentReply.adapter = replyAdapter
            replyAdapter.submitList(comment.replies)
            


        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentWithReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnCommentClicked {

        fun openMenu(comment: Comment)
        fun replyComment(comment: Comment)
    }

}

