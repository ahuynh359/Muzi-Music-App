package com.ahuynh.muzimusicapp.ui.component.user.album.detail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailAlbumBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.album.AlbumViewModel
import com.ahuynh.muzimusicapp.utils.Utils
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAlbumFragment :
    BaseFragment<FragmentDetailAlbumBinding>(FragmentDetailAlbumBinding::inflate),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailAlbumFragment"
    }

    private val songAdapter = SongAdapter(this)
    private val viewModel by viewModels<AlbumViewModel>()
    private lateinit var songOfAlbum: ArrayList<Song>
    private lateinit var currentAlbum: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentAlbum = DetailAlbumFragmentArgs.fromBundle(requireArguments()).album
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        handleUI()
        observe()
    }

    private fun getData() {
        viewModel.getSongOfAlbum(currentAlbum.id)
    }

    private fun observe() {

        viewModel.songOfAlbumList.observe(viewLifecycleOwner) {
            Log.d("ABC",it.size.toString())
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
        binding.rcySongs.adapter = songAdapter
        binding.imvAlbum.loadImage(currentAlbum.avatar)
        binding.tvAlbumName.text = currentAlbum.name


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnPlay.setOnClickListener {
            Utils.sendNewMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOfAlbum[0], songOfAlbum
            )
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility = View.GONE
                    songAdapter.submitList(songOfAlbum)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    private fun filterSongs(query: String) {
        val filteredList = songOfAlbum.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        if (filteredList.isEmpty()) {
            binding.tvNoSongs.visibility = View.VISIBLE
        } else {
            binding.tvNoSongs.visibility = View.GONE
        }
        songAdapter.submitList(filteredList)
    }

    override fun onSongClicked(song: Song) {
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfAlbum
        )
    }

    override fun openMenu(song: Song) {
    }


}