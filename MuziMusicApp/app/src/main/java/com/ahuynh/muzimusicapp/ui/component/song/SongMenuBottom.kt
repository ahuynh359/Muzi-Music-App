package com.ahuynh.muzimusicapp.ui.component.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
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
        binding.btnDelete.setOnClickListener {
            ConfirmDialog(
                requireContext(),
                title = "Delete song",
                message = "Are you sure want to delete ${currentSong.name} ?",
                negativeButtonTitle = "CANCEL",
                positiveButtonTitle = "DELETE",
                callback = object : ConfirmDialog.ConfirmCallBack {
                    override fun negativeAction() {
                        dismiss()
                    }

                    override fun positiveAction() {
                        viewModel.deleteSong(currentSong)
                        viewModel.deleteSong.observe(viewLifecycleOwner){
                            if(it){
                                dismiss()
                            } else {
                                makeErrorToast(requireContext(),"Error when delete song")
                            }
                        }

                    }
                }
            ).show()
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


}