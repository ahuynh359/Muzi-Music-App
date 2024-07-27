package com.ahuynh.muzimusicapp.ui.component.user.singer.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerViewType
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSingerMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingerMenu : BottomSheetDialogFragment(), SingerAdapter.OnSingerClicked {
    companion object {
        const val TAG = "SingerMenu"
    }

    private val singerAdapter = SingerAdapter(this, SingerViewType.LIST)
    private lateinit var binding: FragmentSingerMenuBinding
    private lateinit var currentSong: Song
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentSong = SingerMenuArgs.fromBundle(requireArguments()).song
    }

    private fun initData() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingerMenuBinding.inflate(
            inflater,
            container,
            false
        )


        handleUI()

        return binding.root
    }





    private fun handleUI() {
        binding.rcySinger.adapter = singerAdapter
        singerAdapter.submitList(currentSong.singers)

    }


    override fun onSingerClicked(singer: Singer) {
        val action = SingerMenuDirections.actionSingerMenuToDetailSingerFragment(singer)
        findNavController().navigate(action)
    }



}