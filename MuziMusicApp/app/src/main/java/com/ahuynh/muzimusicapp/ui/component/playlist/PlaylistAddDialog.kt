package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.databinding.FragmentFormPlaylistBinding
import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistAddDialog : DialogFragment() {
    private var _binding: FragmentFormPlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})

    companion object {
        const val TAG = "PlaylistAddDialog"
    }

    private var playlist: Playlist? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormPlaylistBinding.inflate(inflater, container, false)
        playlist = arguments?.parcelable("playlist")


        Log.d(TAG, playlist.toString())

        handleUI()
        return binding.root
    }

    private fun handleUI() {
        binding.btnCancle.setOnClickListener {
            this.dismiss()
        }
        if (playlist != null) {
            binding.btnCreate.text = getString(R.string.edit)
            binding.btnCreate.setOnClickListener {
                playlist?.let {
                    viewModel.updatePlaylist(playlist!!, binding.edtPlaylist.text.toString())
                    this.dismiss()
                }


            }
        } else {

            binding.btnCreate.setOnClickListener {
                val playlist = getCurrentPlaylist()
                playlist?.let {
                    viewModel.addNewPlaylist(playlist)
                    this.dismiss()
                }
            }
        }


    }

    private fun getCurrentPlaylist(): PlaylistModel? {
        if (binding.edtPlaylist.text.toString().isEmpty()) {
            binding.edtPlaylist.error = "Playlist name is empty"
            return null
        }
        return PlaylistModel(binding.edtPlaylist.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}