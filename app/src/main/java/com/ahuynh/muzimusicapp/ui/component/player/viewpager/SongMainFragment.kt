package com.ahuynh.muzimusicapp.ui.component.player.viewpager

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSongMainBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongMainFragment : BaseFragment<FragmentSongMainBinding>(FragmentSongMainBinding::inflate) {

    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    private lateinit var rotateAnimation: ObjectAnimator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        observeViewModel()
    }

    private fun initializeUI() {
        rotateAnimation = ObjectAnimator.ofFloat(binding.imvSong, "rotation", 0f, 360f).apply {
            duration = 10000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
    }

    private fun observeViewModel() {
        viewModel.isPlaying.observe(requireActivity()) { isPlaying ->
            handleRotation(isPlaying)
        }

        viewModel.song.observe(viewLifecycleOwner) { song ->
            updateSongDetails(song)
        }
    }

    private fun handleRotation(isPlaying: Boolean) {
        if (isPlaying) {
            rotateAnimation.cancel()
            rotateAnimation = ObjectAnimator.ofFloat(
                binding.imvSong, "rotation", viewModel.currentRotate, viewModel.currentRotate + 360f
            ).apply {
                duration = 10000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        } else {
            rotateAnimation.pause()
            viewModel.currentRotate = rotateAnimation.animatedValue as Float
        }
    }

    private fun updateSongDetails(song: Song) {
        binding.tvSongName.text = song.name
        binding.imvSong.loadImage(song.avatar)
    }
}