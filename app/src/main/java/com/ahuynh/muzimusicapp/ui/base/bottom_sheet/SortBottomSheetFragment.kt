package com.ahuynh.muzimusicapp.ui.base.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahuynh.muzimusicapp.databinding.FragmentSortBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSortBottomSheetBinding

    interface SortOptionListener {
        fun onSortOptionSelected(name: SortName)
    }

    var listener: SortOptionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isUserManageFragment = arguments?.getBoolean("IS_USER_MANAGE_FRAGMENT") ?: false

        binding.btnNew.setOnClickListener {
            listener?.onSortOptionSelected(SortName.NEW)
            dismiss()
        }

        binding.btnOld.setOnClickListener {
            listener?.onSortOptionSelected(SortName.OLD)
            dismiss()
        }

        binding.btnAZ.setOnClickListener {
            listener?.onSortOptionSelected(SortName.A_Z)
            dismiss()
        }

        binding.btnZA.setOnClickListener {
            listener?.onSortOptionSelected(SortName.Z_A)
            dismiss()
        }

        binding.btnLock.setOnClickListener {
            listener?.onSortOptionSelected(SortName.LOCKED)
            dismiss()
        }

        binding.btnUnlock.setOnClickListener {
            listener?.onSortOptionSelected(SortName.UNLOCKED)
            dismiss()
        }
        if(isUserManageFragment){
            binding.btnLock.visibility = View.VISIBLE
            binding.btnUnlock.visibility = View.VISIBLE
        } else {
            binding.btnLock.visibility = View.GONE
            binding.btnUnlock.visibility = View.GONE
        }
    }
}

enum class SortName {
    A_Z, Z_A, NEW, OLD, LOCKED, UNLOCKED
}
