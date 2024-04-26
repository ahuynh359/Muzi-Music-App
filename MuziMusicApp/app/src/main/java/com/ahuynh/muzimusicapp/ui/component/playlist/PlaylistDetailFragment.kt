package com.ahuynh.muzimusicapp.ui.component.playlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.OnPlaylistSongClicked
import com.ahuynh.muzimusicapp.adapter.PlaylistSongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
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
    OnPlaylistSongClicked {

    companion object {
        const val TAG = "PlaylistDetailFragment"
    }
    private lateinit var songAdapter: PlaylistSongAdapter
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private lateinit var songListOfPlaylist : ArrayList<Song>
    private var needReload = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()

    }

    private fun handleUI() {
        val currentPlaylist = PlaylistDetailFragmentArgs.fromBundle(requireArguments()).playlist

        binding.btnAddMoreItem.setOnClickListener {
            val action = PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToPlaylistDetailAddSongBottomSheet(currentPlaylist)
            findNavController().navigate(action)

        }


        Glide
            .with(binding.imvPlaylist.context)
            .load(currentPlaylist.image)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.note)
            .into(binding.imvPlaylist)
        binding.tvPlaylistName.text = currentPlaylist.name
        binding.btnPlay.setOnClickListener {

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        if (currentPlaylist.songs != null) {
            val idSongs = currentPlaylist.songs!!

            viewModel.getSongsOfPlaylist(currentPlaylist)
            viewModel.songs.observe(viewLifecycleOwner) {

                Log.d("ABC", it.toString())
                songListOfPlaylist = Utils.getSongWithId(it, Constants.SONG_LIST_DATA)
                Log.d("ABC", songListOfPlaylist.toString())

                songAdapter = PlaylistSongAdapter(this, songListOfPlaylist)
                binding.rcySongs.adapter = songAdapter
                binding.rcySongs.visibility = View.VISIBLE
                if (it.isEmpty()) {
                    binding.btnPlay.visibility = View.INVISIBLE
                } else {
                    binding.btnPlay.visibility = View.VISIBLE
                }
            }


        }
        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendMusic(
                requireContext(),
                MusicService.ACTION_PLAY,
                songListOfPlaylist[0], songListOfPlaylist
            )
        }


    }




    override fun onPlaylistSongClicked(song: Song) {
        Toast.makeText(context, song.id.toString(), Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            MusicService.ACTION_PLAY,
            song, songListOfPlaylist
        )
    }



}