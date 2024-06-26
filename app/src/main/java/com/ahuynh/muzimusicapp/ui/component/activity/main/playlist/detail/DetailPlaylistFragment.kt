package com.ahuynh.muzimusicapp.ui.component.activity.main.playlist.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailPlaylistBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.activity.main.home.HomeViewModel
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPlaylistFragment :
    BaseFragment<FragmentDetailPlaylistBinding>(FragmentDetailPlaylistBinding::inflate),
    SongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "DetailPlaylistFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })
    private lateinit var songOfPlaylist: ArrayList<Song>
    private lateinit var currentPlaylist: Playlist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPlaylist = DetailPlaylistFragmentArgs.fromBundle(requireArguments()).playlist
        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getSongOfPlaylist(currentPlaylist.id)
    }

    private fun observe() {


        viewModel.songOfPlaylist.observe(viewLifecycleOwner) {

            songAdapter.submitList(it)
            songOfPlaylist = it as ArrayList<Song>
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
            .load(currentPlaylist.avatar)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imvPlaylist)
        binding.tvPlaylistName.text = currentPlaylist.name

        binding.btnMore.setOnClickListener {


        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendNewMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOfPlaylist[0], songOfPlaylist
            )
        }


    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfPlaylist
        )
    }

    override fun openMenu(song: Song) {
        Toast.makeText(requireContext(), "ABC", Toast.LENGTH_LONG).show()
    }


}