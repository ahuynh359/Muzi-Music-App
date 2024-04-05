package com.ahuynh.muzimusicapp.ui.component.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.databinding.FragmentFormPlaylistBinding
import com.ahuynh.muzimusicapp.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistAddDialog : DialogFragment() {
    private var _binding: FragmentFormPlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PlaylistViewModel>({requireActivity()})
    private val TAG = "FormPlaylistFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormPlaylistBinding.inflate(inflater, container, false)
        handleUI()
        return binding.root
    }

    private fun handleUI() {
        binding.btnCancle.setOnClickListener {
            this.dismiss()
        }
        binding.btnCreate.setOnClickListener {
            val playlist = getCurrentPlaylist()
            playlist?.let {
                viewModel.addNewPlaylist(playlist)
                viewModel.addPlaylistStatus.observe(viewLifecycleOwner) { res ->
                    when (res) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            this.dismiss()
                        }
                        is Response.Failure -> {
                        }

                    }
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