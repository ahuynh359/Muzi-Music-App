package com.ahuynh.muzimusicapp.ui.component.playlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.OnPlaylistSongClicked
import com.ahuynh.muzimusicapp.adapter.PlaylistSongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistDetailBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.SONG_LIST_DATA
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()

    }

    private fun handleUI() {
        val currentPlaylist = PlaylistDetailFragmentArgs.fromBundle(requireArguments()).playlist

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


        if (currentPlaylist.songs != null) {
            val idSongs = currentPlaylist.songs
            songListOfPlaylist = getSongWithId(idSongs, SONG_LIST_DATA)
            songAdapter = PlaylistSongAdapter(this, songListOfPlaylist)
            binding.rcySongs.adapter = songAdapter
            binding.rcySongs.visibility = View.VISIBLE
        }

        binding.btnAddMoreItem.setOnClickListener {
            
        }
    }

    private fun getSongWithId(songIds: List<String>, songs: List<Song>): ArrayList<Song> {
        val songIdSet = songIds.toSet()
        return songs.filter { it.id in songIdSet } as ArrayList<Song>
    }


    override fun onPlaylistSongClicked(song: Song) {
        Toast.makeText(context, song.id.toString(), Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendMusic(
            requireContext(),
            Constants.ACTION_PLAY,
            song, songListOfPlaylist
        )
    }



}