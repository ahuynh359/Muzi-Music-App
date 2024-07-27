package com.ahuynh.muzimusicapp.ui.component.user.song.add_song_to_playlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentAddSongToPlaylistBottomSheetBinding
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistFragmentDirections
import com.ahuynh.muzimusicapp.ui.component.user.playlist.add_new_playlist.PlaylistAddFragment
import com.ahuynh.muzimusicapp.ui.component.user.playlist.menu.PlaylistMenu
import com.ahuynh.muzimusicapp.ui.component.user.song.menu.SongMenu
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSongToPlaylistFragment : BottomSheetDialogFragment(),
    PlaylistAdapter.OnPlaylistClicked {
    companion object {
        const val TAG = "AddSongToPlaylistFragment"
    }

    private lateinit var binding: FragmentAddSongToPlaylistBottomSheetBinding
    private val playlistAdapter = PlaylistAdapter(this,false)

    private val viewModel by viewModels<AddSongToPlaylistViewModel>({ requireActivity() })
    private lateinit var currentSong: Song
    private var playlistList: ArrayList<Playlist> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val song: Song? = arguments?.parcelable(Constants.SONG)
        if (song == null) dismiss()
        else currentSong = song
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPlaylists()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSongToPlaylistBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )

        handleUI()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.playlists.observe(viewLifecycleOwner) {
            playlistAdapter.submitList(it)
            playlistList = it as ArrayList<Playlist>
        }
        viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner) {
            if (it == true) {
                dismiss()
                viewModel.addSongToPlaylistStatus.postValue(null)
            }
        }
    }

    private fun handleUI() {
        binding.btnAddSong.setOnClickListener {
            val fragment = PlaylistAddFragment()
            fragment.show(requireActivity().supportFragmentManager, null)
            dismiss()
        }
        binding.rcyPlaylist.adapter = playlistAdapter

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoResult.visibility = View.GONE
                    playlistAdapter.submitList(playlistList)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterSongs(query: String) {
        val filteredList = playlistList.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        if (filteredList.isEmpty()) {
            binding.tvNoResult.visibility = View.VISIBLE
        } else {
            binding.tvNoResult.visibility = View.GONE
        }
        playlistAdapter.submitList(filteredList)
    }

    override fun onPlaylistClicked(playlist: Playlist) {
        viewModel.addSongToPlaylist(playlist.id, currentSong.id)
        Toast.makeText(requireContext(), playlist.name, Toast.LENGTH_SHORT).show()
    }

    override fun onMoreItemClicked(playlist: Playlist) {

    }


}