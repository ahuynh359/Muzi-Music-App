package com.ahuynh.muzimusicapp.ui.component.player

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentSongMainBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SongMainFragment : BaseFragment<FragmentSongMainBinding>(FragmentSongMainBinding::inflate) {

    private val viewModel by viewModels<PlayerViewModel>({ requireActivity() })
    private lateinit var rotateAnimation : ObjectAnimator
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        observeData()
    }


    private fun observeData() {
        rotateAnimation = ObjectAnimator.ofFloat(binding.imvSong,"rotation",0f,360f)
        viewModel.isPlaying.observe(requireActivity()){
            rotateImage(it)
        }




        viewModel.song.observe(viewLifecycleOwner) { song ->

            binding.tvSongName.text = song.name
            //binding.tvSinger.text = song.singer

            Glide
                .with(binding.imvSong.context)
                .load(song.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvSong);

        }
    }
    private fun rotateImage(rotate : Boolean){
        if(rotate){
            rotateAnimation.cancel()
            rotateAnimation = ObjectAnimator.ofFloat(binding.imvSong,"rotation",viewModel.currentRotate, viewModel.currentRotate + 360f)
            val i = Utils.convertDpToPixel(350f,requireContext()).toFloat()
            rotateAnimation.duration = 10000
            binding.imvSong.pivotX = i / 2
            binding.imvSong.pivotY = i / 2
            rotateAnimation.repeatCount = ObjectAnimator.INFINITE
            rotateAnimation.interpolator = LinearInterpolator()
            rotateAnimation.start()
        } else{
            rotateAnimation.pause()
            viewModel.currentRotate = rotateAnimation.animatedValue as Float
        }
    }

    private fun handleUI() {
    }
}