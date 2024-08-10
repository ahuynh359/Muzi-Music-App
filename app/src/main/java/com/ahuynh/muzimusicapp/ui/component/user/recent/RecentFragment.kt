package com.ahuynh.muzimusicapp.ui.component.user.recent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.adapter.SongEntityAdapter
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailPlaylistBinding
import com.ahuynh.muzimusicapp.databinding.FragmentRecentBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.component.user.playlist.detail_playlist.DetailPlaylistFragmentArgs
import com.ahuynh.muzimusicapp.ui.component.user.playlist.detail_playlist.DetailPlaylistFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment :
    BaseDialogBottomSheetFragment(),
    SongEntityAdapter.OnSongEntityClick {

    companion object {
        const val TAG = "RecentFragment"
    }

    private val songAdapter = SongEntityAdapter(this,SongEntityAdapter.TYPE_SONG_ENTITY_RECENTLY)
    private val viewModel by viewModels<RecentViewModel>()
    private var recentSongList: ArrayList<Song> = arrayListOf()
    private lateinit var binding: FragmentRecentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()

    }

    private fun getData() {
        viewModel.getRecentSongs()
    }

    private fun observe() {


        viewModel.recentSong.observe(viewLifecycleOwner) {
            binding.rcySongs.visibility = View.VISIBLE
            songAdapter.submitList(it)
            recentSongList = it as ArrayList<Song>
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE
        }


    }

    private fun handleUI() {


        binding.btnBack.setOnClickListener {
            dismiss()
        }




    }


    override fun onSongEntityClick(songEntity: SongEntity) {
        val song = songEntity.toSong()
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, arrayListOf(song)
        )
    }

    override fun openMenu(songEntity: SongEntity) {
        val fragment = SongMenu()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.SONG, songEntity.toSong())
        }
        fragment.show(requireActivity().supportFragmentManager, null)
    }


}