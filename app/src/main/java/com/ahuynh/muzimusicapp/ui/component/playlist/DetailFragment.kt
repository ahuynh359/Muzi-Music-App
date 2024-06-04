package com.ahuynh.muzimusicapp.ui.component.playlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.databinding.DetailFragmentBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private lateinit var songOldListOfPlaylist : ArrayList<SongOld>
    private lateinit var currentPlaylist: Playlist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPlaylist = DetailFragmentArgs.fromBundle(requireArguments()).playlist
        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        if (currentPlaylist.songs != null) {
            viewModel.getSongsOfPlaylist(currentPlaylist)
        }
    }

    private fun observe() {
        viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner) {
            if (it) {
                getData()
            }
        }

        viewModel.songs.observe(viewLifecycleOwner) {

            songOldListOfPlaylist = Utils.getSongWithId(it, Constants.SONG_Old_LIST_DATA)
            songAdapter.submitList(songOldListOfPlaylist)



            binding.rcySongs.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.btnPlay.visibility = View.INVISIBLE
            } else {
                binding.btnPlay.visibility = View.VISIBLE
            }
        }


    }

    private fun handleUI() {
        Glide
            .with(binding.imvPlaylist.context)
            .load(currentPlaylist.image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.note)
            .into(binding.imvPlaylist)
        binding.tvPlaylistName.text = currentPlaylist.name

        binding.btnAddMoreItem.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToPlaylistDetailAddSongBottomSheet(currentPlaylist)
            findNavController().navigate(action)

        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOldListOfPlaylist[0], songOldListOfPlaylist
            )
        }


    }

    override fun onSongClicked(songOld: SongOld) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            songOld, songOldListOfPlaylist
        )
    }

    override fun openMenu(songOld: SongOld) {
        val action = DetailFragmentDirections.actionDetailFragmentToSongMenuBottom(songOld,currentPlaylist)
        findNavController().navigate(action)
    }


}