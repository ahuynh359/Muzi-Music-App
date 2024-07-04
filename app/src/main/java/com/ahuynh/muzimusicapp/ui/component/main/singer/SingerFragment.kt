package com.ahuynh.muzimusicapp.ui.component.main.singer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailSingerBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSingerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.main.singer.detail.DetailSingerFragmentArgs
import com.ahuynh.muzimusicapp.ui.component.main.song.SongModelBottomSheet
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingerFragment : BaseFragment<FragmentSingerBinding>(FragmentSingerBinding::inflate)
    , SingerAdapter.OnSingerClicked {

    companion object {
        const val TAG = "SingerFragment"
    }
    private  val singerAdapter = SingerAdapter(this)
    private val viewModel by viewModels<SingerViewModel>()
    private lateinit var singerList: ArrayList<Singer>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllSinger()
    }

    private fun observe() {

        viewModel.singerList.observe(viewLifecycleOwner) {
            singerAdapter.submitList(it)
            singerList = it as ArrayList<Singer>
            binding.rcySinger.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.tvNoSinger.visibility = View.VISIBLE
            } else {
                binding.tvNoSinger.visibility = View.GONE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }


    }



    private fun handleUI() {

        binding.rcySinger.adapter = singerAdapter


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }




    }


    override fun onSingerClicked(singer: Singer) {

    }


}