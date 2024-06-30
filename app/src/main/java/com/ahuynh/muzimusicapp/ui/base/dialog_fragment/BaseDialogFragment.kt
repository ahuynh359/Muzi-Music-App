package com.ahuynh.muzimusicapp.ui.base.dialog_fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {



    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}