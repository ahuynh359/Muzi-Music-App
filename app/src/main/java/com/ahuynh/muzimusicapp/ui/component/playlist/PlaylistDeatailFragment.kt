package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.view.View
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.DetailFragmentBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistDeatailFragment :
    BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate),
    SongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "PlaylistDeatailFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private lateinit var songOldListOfPlaylist : ArrayList<Song>
    private lateinit var currentPlaylist: Playlist

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPlaylist = PlaylistDeatailFragmentArgs.fromBundle(requireArguments()).playlist
        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()

    }

    private fun getData() {
//        if (currentPlaylist.songs != null) {
//            viewModel.getSongsOfPlaylist(currentPlaylist)
//        }
    }

    private fun observe() {
//        viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner) {
//            if (it) {
//                getData()
//            }
//        }
//
//        viewModel.songs.observe(viewLifecycleOwner) {
//
//            songOldListOfPlaylist = Utils.getSongWithId(it, Constants.SONG_Old_LIST_DATA)
//            songAdapter.submitList(songOldListOfPlaylist)
//
//
//
//            binding.rcySongs.visibility = View.VISIBLE
//            if (it.isEmpty()) {
//                binding.btnPlay.visibility = View.INVISIBLE
//            } else {
//                binding.btnPlay.visibility = View.VISIBLE
//            }
//        }
//

    }

    private fun handleUI() {
//        Glide
//            .with(binding.imvPlaylist.context)
//            .load(currentPlaylist.image)
//            .centerCrop()
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .placeholder(R.drawable.note)
//            .into(binding.imvPlaylist)
//        binding.tvPlaylistName.text = currentPlaylist.name
//
//        binding.btnAddMoreItem.setOnClickListener {
//            val action = DetailFragmentDirections.actionDetailFragmentToPlaylistDetailAddSongBottomSheet(currentPlaylist)
//            findNavController().navigate(action)
//
//        }
//
//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//
//        binding.btnPlay.setOnClickListener {
//            startActivity(Intent(requireContext(), PlayerActivity::class.java))
//            Utils.sendMusic(
//                requireActivity(),
//                MusicService.ACTION_PLAY,
//                songOldListOfPlaylist[0], songOldListOfPlaylist
//            )
//        }


    }

    override fun onSongClicked(song: Song) {
        TODO("Not yet implemented")
    }

    override fun openMenu(song: Song) {
        TODO("Not yet implemented")
    }

//    override fun onSongClicked(songOld: SongOld) {
//        startActivity(Intent(requireContext(), PlayerActivity::class.java))
//        Utils.sendMusic(
//            requireActivity(),
//            MusicService.ACTION_PLAY,
//            songOld, songOldListOfPlaylist
//        )
//    }
//
//    override fun openMenu(song: SongOld) {
//        val action = DetailFragmentDirections.actionDetailFragmentToSongMenuBottom(songOld,currentPlaylist)
//        findNavController().navigate(action)
//    }


}