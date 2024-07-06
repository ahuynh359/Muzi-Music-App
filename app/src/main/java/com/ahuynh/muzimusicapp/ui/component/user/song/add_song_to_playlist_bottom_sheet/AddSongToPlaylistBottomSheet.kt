package com.ahuynh.muzimusicapp.ui.component.user.song.add_song_to_playlist_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentAddSongToPlaylistBottomSheetBinding
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSongToPlaylistBottomSheet : BottomSheetDialogFragment(),PlaylistAdapter.OnPlaylistClicked{
    companion object {
        const val TAG = "AddSongToPlaylistBottomSheet"
    }

    private lateinit var binding: FragmentAddSongToPlaylistBottomSheetBinding
    private  val playlistAdapter = PlaylistAdapter(this)

    private val viewModel by viewModels<AddSongToPlaylistBottomSheetViewModel>({requireActivity()})
    private lateinit var currentSong: Song


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        currentSong = arguments?.parcelable<Song>(Constants.SONG)!!
        handleUI()
        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.playlists.observe(viewLifecycleOwner){
            playlistAdapter.submitList(it)
        }
        viewModel.addSongToPlaylistStatus.observe(viewLifecycleOwner){
            if(it == true){
                dismiss()
                viewModel.addSongToPlaylistStatus.postValue(null)
            }
        }
    }

    private fun handleUI() {
        binding.rcyPlaylist.adapter = playlistAdapter
    }

    override fun onPlaylistClicked(playlist: Playlist) {
        viewModel.addSongToPlaylist(playlist.id,currentSong.id)
        Toast.makeText(requireContext(),playlist.name,Toast.LENGTH_SHORT).show()
    }

    override fun onMoreItemClicked(playlist: Playlist) {

    }


}