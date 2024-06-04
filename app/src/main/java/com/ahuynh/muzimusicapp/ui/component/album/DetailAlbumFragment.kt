package com.ahuynh.muzimusicapp.ui.component.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.NewSongAdapter
import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailAlbumBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.song.SongViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAlbumFragment :
    BaseFragment<FragmentDetailAlbumBinding>(FragmentDetailAlbumBinding::inflate),
    NewSongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "DetailAlbumFragment"
    }

    private lateinit var songAdapter: NewSongAdapter
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })
    private lateinit var songOfAlbum: ArrayList<Song>
    private lateinit var currentAlbum: Album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAlbum = DetailAlbumFragmentArgs.fromBundle(requireArguments()).album
        songAdapter = NewSongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getSongOfAlbum(currentAlbum.id)
    }

    private fun observe() {


        viewModel.songOfAlbum.observe(viewLifecycleOwner) {

            songAdapter.submitList(it)
            songOfAlbum = it as ArrayList<Song>
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
            .load(currentAlbum.avatar)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.note)
            .into(binding.imvPlaylist)
        binding.tvAlbumName.text = currentAlbum.name

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
                songOfAlbum[0], songOfAlbum
            )
        }


    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfAlbum
        )
    }

    override fun openMenu(song: Song) {
        Toast.makeText(requireContext(), "ABC", Toast.LENGTH_LONG).show()
    }


}