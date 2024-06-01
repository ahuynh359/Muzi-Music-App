package com.ahuynh.muzimusicapp.ui.component.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.databinding.FragmentSongMenuBottomBinding
import com.ahuynh.muzimusicapp.ui.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.utils.helper.ToastHelper.makeErrorToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SongMenuBottom : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "SongMenuBottom"
    }

    private lateinit var binding: FragmentSongMenuBottomBinding
    private val viewModel by viewModels<SongViewModel>({ requireActivity() })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongMenuBottomBinding.inflate(
            inflater,
            container,
            false
        )

        handleUI()
        return binding.root
    }

    private fun handleUI() {
        val currentSong = SongMenuBottomArgs.fromBundle(requireArguments()).song
        val playlist = SongMenuBottomArgs.fromBundle(requireArguments()).playlist

        binding.btnDelete.setOnClickListener {
            //Delete song from system
            if (playlist == null) {
                showDialogConfirm(
                    "Confirm delete song",
                    "Do you want to delete song ${currentSong.name}",
                    currentSong
                )
            }
            //Delete song from playlist
            else {
                showDialogConfirm(
                    "Confirm delete song",
                    "Do you want to delete song ${currentSong.name} from ${playlist.name}",
                    currentSong,
                    playlist
                )
            }
        }

        if (currentSong.love == true) {
            binding.tvLove.text = "Unlove"
            binding.icHeart.setImageResource(R.drawable.ic_heart_small)
        } else {
            binding.tvLove.text = "Love"
            binding.icHeart.setImageResource(R.drawable.ic_hearted)
        }
        binding.btnLove.setOnClickListener {
            viewModel.updateSongLoveStatus(currentSong.id!!, !currentSong.love)
            viewModel.loveSong.observe(viewLifecycleOwner) {
                if (it) {
                    dismiss()
                } else {
                    makeErrorToast(requireContext(),"Error when love song")
                }
            }

        }
    }

    private fun showDialogConfirm(
        title: String,
        message: String,
        currentSong: Song? = null,
        currentPlaylist: Playlist? = null
    ) {
        ConfirmDialog(
            requireContext(),
            title = title,
            message = message,
            negativeButtonTitle = "CANCEL",
            positiveButtonTitle = "DELETE",
            callback = object : ConfirmDialog.ConfirmCallBack {
                override fun negativeAction() {
                    dismiss()
                }

                override fun positiveAction() {
                    if (currentPlaylist == null && currentSong != null) {
                        viewModel.deleteSong(currentSong!!)
                        viewModel.deleteSong.observe(viewLifecycleOwner) {
                            if (it) {
                                dismiss()
                            } else {
                                makeErrorToast(requireContext(), "Error when delete song from system")
                            }
                        }
                    }  else if(currentPlaylist != null){
                        viewModel.deleteSongFromPlaylist(currentPlaylist!!,currentSong!!)
                        viewModel.deleteSongFromPlaylist.observe(viewLifecycleOwner) {
                            if (it) {
                                dismiss()
                            } else {
                                makeErrorToast(requireContext(), "Error when delete song from system")
                            }
                        }
                    }


                }

            }
        ).show()
    }


}