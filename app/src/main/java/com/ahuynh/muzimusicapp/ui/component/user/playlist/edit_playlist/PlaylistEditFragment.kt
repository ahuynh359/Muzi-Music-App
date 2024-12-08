package com.ahuynh.muzimusicapp.ui.component.user.playlist.edit_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistEditBinding
import com.ahuynh.muzimusicapp.ui.base.dialog_fragment.BaseDialogFragment
import com.ahuynh.muzimusicapp.ui.component.user.playlist.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistEditFragment : BaseDialogFragment() {
    private var _binding: FragmentPlaylistEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })
    private lateinit var currentPlaylist: Playlist

    companion object {
        const val TAG = "PlaylistEditFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistEditBinding.inflate(inflater, container, false)
        currentPlaylist = PlaylistEditFragmentArgs.fromBundle(requireArguments()).playlist
        handleUI()
        observeData()
        return binding.root
    }

    private fun observeData() {
        viewModel.updatePlaylistStatus.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    this.dismiss()
                } else {
                    Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    private fun handleUI() {
        binding.edtPlaylist.setText(currentPlaylist.name)
        binding.btnCancle.setOnClickListener {
            this.dismiss()
        }

        binding.btnEdit.setOnClickListener {
            val playlist = getCurrentPlaylist()
            playlist?.let {
                viewModel.updatePlaylist(playlist, currentPlaylist.id)
            }


        }


    }

    private fun getCurrentPlaylist(): String? {
        if (binding.edtPlaylist.text.toString().isEmpty()) {
            binding.edtPlaylist.error = "Playlist name is empty"
            return null
        }
        return binding.edtPlaylist.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}