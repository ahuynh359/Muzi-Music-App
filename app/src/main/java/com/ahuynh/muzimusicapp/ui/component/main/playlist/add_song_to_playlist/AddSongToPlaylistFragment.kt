package com.ahuynh.muzimusicapp.ui.component.main.playlist.add_song_to_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.databinding.FragmentAddSongToPlaylistBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSongToPlaylistFragment :
    BaseDialogBottomSheetFragment() {

    companion object {
        const val TAG = "AddSongToPlaylistFragment"
    }


    private lateinit var binding: FragmentAddSongToPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSongToPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()


    }

    private fun handleUI() {

    }


}