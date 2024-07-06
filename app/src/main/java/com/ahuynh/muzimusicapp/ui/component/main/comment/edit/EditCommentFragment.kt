package com.ahuynh.muzimusicapp.ui.component.main.comment.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.databinding.FragmentEditCommentBinding
import com.ahuynh.muzimusicapp.ui.base.dialog_fragment.BaseDialogFragment
import com.ahuynh.muzimusicapp.ui.component.main.comment.CommentViewModel
import com.ahuynh.muzimusicapp.ui.component.main.comment.menu.CommentMenuArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCommentFragment : BaseDialogFragment() {
    private var _binding: FragmentEditCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CommentViewModel>({ requireActivity() })
    private lateinit var currentComment: Comment

    companion object {
        const val TAG = "PlaylistAddFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentComment = CommentMenuArgs.fromBundle(requireArguments()).comment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCommentBinding.inflate(inflater, container, false)
        handleUI()
        observeData()
        return binding.root
    }

    private fun observeData() {
    viewModel.updateCommentStatus.observe(viewLifecycleOwner){
        if(it==true){
            dismiss()
        }
    }

    }

    private fun handleUI() {
        binding.btnCancle.setOnClickListener {
            this.dismiss()
        }

        binding.btnOk.setOnClickListener {
            getCommentName()?.let {
                val editCommentRequest = EditCommentRequest(it, currentComment.id)
                viewModel.editComment(editCommentRequest)

            }


        }


    }

    private fun getCommentName(): String? {
        if (binding.edtComment.text.toString().isEmpty()) {
            binding.edtComment.error = "Comment is empty"
            return ""
        }
        return binding.edtComment.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}