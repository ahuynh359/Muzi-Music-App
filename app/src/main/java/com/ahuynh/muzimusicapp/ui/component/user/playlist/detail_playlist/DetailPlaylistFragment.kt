package com.ahuynh.muzimusicapp.ui.component.user.playlist.detail_playlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailPlaylistBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPlaylistFragment :
    BaseDialogBottomSheetFragment(),
    SongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "DetailPlaylistFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    private lateinit var songOfPlaylist: ArrayList<Song>
    private lateinit var currentPlaylist: Playlist
    private lateinit var binding: FragmentDetailPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPlaylist = DetailPlaylistFragmentArgs.fromBundle(requireArguments()).playlist

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPlaylistBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            dismiss()
        }
        binding.btnAddSong.setOnClickListener {
            val action = DetailPlaylistFragmentDirections.actionDetailPlaylistFragmentToAddSongToPlaylistFragment(currentPlaylist)
            findNavController().navigate(action)
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