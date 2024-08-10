package com.ahuynh.muzimusicapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.ItemTypeBinding
import com.ahuynh.muzimusicapp.databinding.ItemTypeFullBinding
import com.ahuynh.muzimusicapp.databinding.ItemTypeHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

enum class TypeViewType {
    SIMPLE, FULL, HOME
}

class TypeAdapter(
    private val listener: OnTypeClicked,
    private val viewType: TypeViewType,
    private val hideBtnMore : Boolean = false
) : ListAdapter<Type, RecyclerView.ViewHolder>(DiffCallback()) {

    inner class SimpleViewHolder(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(currentList[layoutPosition])
            }
        }

        fun bind(type: Type) {
            Glide
                .with(binding.imvType.context)
                .load(type.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvType)
            binding.tvName.text = type.name
        }
    }

    inner class FullViewHolder(private val binding: ItemTypeFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(currentList[layoutPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreClicked(currentList[layoutPosition])
            }
        }

        fun bind(type: Type) {
            binding.imvType.load(type.avatar)
            binding.tvTypeName.text = type.name
            if(hideBtnMore){
                binding.btnMore.visibility = View.GONE
            } else
                binding.btnMore.visibility = View.VISIBLE
        }
    }

    inner class HomeViewHolder(private val binding: ItemTypeHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(currentList[layoutPosition])
            }

        }

        fun bind(type: Type) {
            binding.tvTypeName.text = type.name
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            TypeViewType.SIMPLE -> {
                val binding =
                    ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SimpleViewHolder(binding)
            }

            TypeViewType.FULL -> {
                val binding =
                    ItemTypeFullBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FullViewHolder(binding)
            }

            TypeViewType.HOME -> {
                val binding =
                    ItemTypeHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = currentList[position]
        when (holder) {
            is SimpleViewHolder -> holder.bind(type)
            is FullViewHolder -> holder.bind(type)
            is HomeViewHolder -> holder.bind(type)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnTypeClicked {
        fun onTypeClicked(type: Type)
        fun onMoreClicked(type: Type)
    }
}