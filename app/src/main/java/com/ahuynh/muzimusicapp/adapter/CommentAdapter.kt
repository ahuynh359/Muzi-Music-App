package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.databinding.ItemCommentWithReplyBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage

class CommentAdapter(
    private val listener: OnCommentClicked,
    private val hideBtnReply: Boolean = false
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var comments: List<Comment> = emptyList()

    fun submitList(data: List<Comment>) {
        comments = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCommentWithReplyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnMore.setOnClickListener {
                listener.openMenu(comments[bindingAdapterPosition])
            }
            binding.btnReply.setOnClickListener {
                listener.replyComment(comments[bindingAdapterPosition])
            }
        }

        fun bind(comment: Comment) {
            binding.apply {
                imvAvatar.loadImage(comment.user.avatar)
                tvName.text = comment.user.username
                tvContent.text = comment.content
                tvTime.text = comment.time
                tvTime.isSelected = true
                btnReply.visibility = if (hideBtnReply) View.GONE else View.VISIBLE

                val replyAdapter = CommentAdapter(listener, hideBtnReply = true)
                rcyCommentReply.adapter = replyAdapter
                replyAdapter.submitList(comment.replies)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentWithReplyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size

    interface OnCommentClicked {
        fun openMenu(comment: Comment)
        fun replyComment(comment: Comment)
    }
}