package com.ahuynh.muzimusicapp.ui.base.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ahuynh.muzimusicapp.databinding.DialogConfirmBinding

class ConfirmDialog(
    context: Context,
    private val callback: ConfirmCallBack?,
    private val title: String,
    private val message: String,
    private val positiveButtonTitle: String,
    private val negativeButtonTitle: String
) : Dialog(context) {
    private lateinit var binding: DialogConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = title
        binding.tvContent.text = message
        binding.btnNegative.setOnClickListener {
            callback?.negativeAction()
            dismiss()
        }
        binding.btnNegative.text = negativeButtonTitle
        binding.btnPositive.setOnClickListener {
            callback?.positiveAction()
            dismiss()
        }
        binding.btnPositive.text = positiveButtonTitle

    }
    interface ConfirmCallBack {
        fun negativeAction()
        fun positiveAction()
    }
}


