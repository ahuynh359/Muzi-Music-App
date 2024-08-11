package com.ahuynh.muzimusicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.ItemTypeBinding
import com.ahuynh.muzimusicapp.databinding.ItemTypeFullBinding
import com.ahuynh.muzimusicapp.databinding.ItemTypeHomeBinding

class TypeAdapter(
    private val listener: OnTypeClicked,
    private val viewType: TypeViewType,
    private val hideBtnMore: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var types: List<Type> = emptyList()

    fun submitList(data: List<Type>) {
        types = data
        notifyDataSetChanged()
    }

    inner class SimpleViewHolder(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(types[bindingAdapterPosition])
            }
        }

        fun bind(type: Type) {
            binding.imvType.load(type.avatar)
            binding.tvName.text = type.name
        }
    }

    inner class FullViewHolder(private val binding: ItemTypeFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(types[bindingAdapterPosition])
            }
            binding.btnMore.setOnClickListener {
                listener.onMoreClicked(types[bindingAdapterPosition])
            }
        }

        fun bind(type: Type) {
            binding.imvType.load(type.avatar)
            binding.tvTypeName.text = type.name
            binding.btnMore.visibility = if (hideBtnMore) View.GONE else View.VISIBLE
        }
    }

    inner class HomeViewHolder(private val binding: ItemTypeHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onTypeClicked(types[bindingAdapterPosition])
            }
        }

        fun bind(type: Type) {
            binding.tvTypeName.text = type.name
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
        val type = types[position]
        when (holder) {
            is SimpleViewHolder -> holder.bind(type)
            is FullViewHolder -> holder.bind(type)
            is HomeViewHolder -> holder.bind(type)
        }
    }

    override fun getItemCount(): Int = types.size

    interface OnTypeClicked {
        fun onTypeClicked(type: Type)
        fun onMoreClicked(type: Type)
    }

    enum class TypeViewType {
        SIMPLE, FULL, HOME
    }
}