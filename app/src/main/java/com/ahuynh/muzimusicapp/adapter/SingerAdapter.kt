package com.ahuynh.muzimusicapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter.HomeViewHolder
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter.ListViewHolder
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.ItemBinding
import com.ahuynh.muzimusicapp.databinding.ItemCircleBigBinding
import com.ahuynh.muzimusicapp.utils.Utils.loadImage


class SingerAdapter(
    private val listener: OnSingerClicked,
    private val viewType: SingerViewType,
    private val hideBtnMore: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var singers: List<Singer> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Singer>) {
        singers = data
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: ItemCircleBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSingerClicked(singers[bindingAdapterPosition])
            }
        }

        fun bind(singer: Singer) {
            binding.imv.loadImage(singer.avatar)
            binding.tvName.text = singer.name
            binding.tvDes.text = binding.root.context.getString(
                R.string.singer
            )
            binding.tvName.isSelected = true

        }
    }

    inner class ListViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onSingerClicked(singers[bindingAdapterPosition])
            }
        }

        fun bind(singer: Singer) {
            binding.imv.loadImage(singer.avatar)
            binding.tvName.text = singer.name
            binding.tvDes.text = binding.root.context.getString(
                R.string.singer
            )
            binding.btnMore.visibility = if (hideBtnMore) View.GONE else View.VISIBLE
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            SingerViewType.HOME -> {
                HomeViewHolder(
                    ItemCircleBigBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            SingerViewType.LIST -> {
                ListViewHolder(
                    ItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(singers[position])
            is ListViewHolder -> holder.bind(singers[position])
        }
    }

    override fun getItemCount(): Int {
        return singers.size
    }

    interface OnSingerClicked {
        fun onSingerClicked(singer: Singer)
    }

    enum class SingerViewType {
        HOME, LIST
    }

}