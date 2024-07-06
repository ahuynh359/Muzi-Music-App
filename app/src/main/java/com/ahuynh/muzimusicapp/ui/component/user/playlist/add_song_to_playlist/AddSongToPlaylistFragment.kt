package com.ahuynh.muzimusicapp.ui.component.user.playlist.add_song_to_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.SongAddAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentAddSongToPlaylistBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.ui.component.user.playlist.detail_playlist.DetailPlaylistFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSongToPlaylistFragment :
    BaseDialogBottomSheetFragment(), SongAddAdapter.OnSongAddClicked {

    companion object {
        const val TAG = "AddSongToPlaylistFragment"
    }

    private val songAdapter = SongAddAdapter(this)
    private lateinit var binding: FragmentAddSongToPlaylistBinding
    private lateinit var currentPlaylist: Playlist
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPlaylist = DetailPlaylistFragmentArgs.fromBundle(requireArguments()).playlist
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSongToPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllSongsNotInPlaylist(currentPlaylist.id)
    }

    private fun observe() {
        viewModel.listSongNotInPlaylist.observe(viewLifecycleOwner) {
            songAdapter.submitList(it)
        }
        viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner) {
            viewModel.mess = null

        }
    }

    private fun handleUI() {
        binding.rcySong.adapter = songAdapter
        binding.btnBack.setOnClickListener { dismiss() }

    }


    override fun onSongAdd(song: Song) {
        viewModel.addSongToPlaylist(currentPlaylist.id, song.id)
    }


}