package com.ahuynh.muzimusicapp.ui.component.playlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistDetailBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistDetailFragment :
    BaseFragment<FragmentPlaylistDetailBinding>(FragmentPlaylistDetailBinding::inflate),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "PlaylistDetailFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private lateinit var songListOfPlaylist : ArrayList<Song>
    private lateinit var currentPlaylist: Playlist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPlaylist = PlaylistDetailFragmentArgs.fromBundle(requireArguments()).playlist
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

            songListOfPlaylist = Utils.getSongWithId(it, Constants.SONG_LIST_DATA)
            songAdapter.submitList(songListOfPlaylist)



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
            val action = PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToPlaylistDetailAddSongBottomSheet(currentPlaylist)
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
                songListOfPlaylist[0], songListOfPlaylist
            )
        }


    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songListOfPlaylist
        )
    }

    override fun openMenu(song: Song) {
        val action = PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToSongMenuBottom(song,currentPlaylist)
        findNavController().navigate(action)
    }


}