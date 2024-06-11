package com.ahuynh.muzimusicapp.ui.component.playlist.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.databinding.DialogPlaylistAddBinding
import com.ahuynh.muzimusicapp.ui.component.playlist.PlaylistViewModel
import com.ahuynh.muzimusicapp.utils.Utils.parcelable
import com.ahuynh.muzimusicapp.utils.helper.ToastHelper.makeErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistAddDialog : DialogFragment() {
    private var _binding: DialogPlaylistAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PlaylistViewModel>({ requireActivity() })

    companion object {
        const val TAG = "PlaylistAddDialog"
    }

    private var playlist: Playlist? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPlaylistAddBinding.inflate(inflater, container, false)
        playlist = arguments?.parcelable("playlist")

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

                dismiss()

            }
            if(viewModel.mess != null){
                Toast.makeText(requireContext(),viewModel.mess,Toast.LENGTH_LONG).show()
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