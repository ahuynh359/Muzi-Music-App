package com.ahuynh.muzimusicapp.ui.component.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentSongMainBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongMainFragment : BaseFragment<FragmentSongMainBinding>(FragmentSongMainBinding::inflate) {

    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()
    }


    private fun observeData() {


        viewModel.song.observe(viewLifecycleOwner) { song ->

            binding.tvSongName.text = song.name
            binding.tvSinger.text = song.singer

            Glide
                .with(binding.imvSong.context)
                .load(song.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(com.ahuynh.muzimusicapp.R.drawable.big_song)
                .into(binding.imvSong);
        }
    }

    private fun handleUI() {
    }
}