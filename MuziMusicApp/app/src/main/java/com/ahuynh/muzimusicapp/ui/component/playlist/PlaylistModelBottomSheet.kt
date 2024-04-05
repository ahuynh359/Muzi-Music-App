package com.ahuynh.muzimusicapp.ui.component.playlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.databinding.DialogModelBottomSheetPlaylistBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaylistModelBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }
    private lateinit var binding: DialogModelBottomSheetPlaylistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogModelBottomSheetPlaylistBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        dialog?.setOnShowListener { it ->
//            val d = it as BottomSheetDialog
//            val bottomSheet =
//                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//            bottomSheet?.let {
//                val behavior = BottomSheetBehavior.from(it)
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            }
//        }
        return super.onCreateDialog(savedInstanceState)
    }


}