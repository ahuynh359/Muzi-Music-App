package com.ahuynh.muzimusicapp.ui.component.main.comment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.CommentAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment :
    BottomSheetDialogFragment(), CommentAdapter.OnCommentClicked {

    companion object {
        const val TAG = "CommentFragment"
    }

    private lateinit var currentSong: Song

    private lateinit var binding: FragmentCommentBinding
    private val viewModel by viewModels<CommentViewModel>({requireActivity()})
    private val commentAdapter = CommentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSong = CommentFragmentArgs.fromBundle(requireArguments()).song


    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val layoutParams = sheet.layoutParams
                layoutParams.height = getBottomSheetDialogDefaultHeight()
                sheet.layoutParams = layoutParams
            }
        }
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return (getScreenHeight() * 0.7).toInt()
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//
        handleUI()
        observe()
        getData()
    }

    private fun observe() {
        viewModel.commentList.observe(viewLifecycleOwner) {
            binding.rcyComment.visibility = View.VISIBLE
            if (it != null) {
                //newSongList = it as ArrayList<Song>
                commentAdapter.submitList(it)
            }


        }

        viewModel.totalComments.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.tvNoComment.visibility = View.INVISIBLE
                binding.tvComment.text = it.toString() + " Comments"
            } else {
                binding.tvNoComment.visibility = View.VISIBLE
                binding.tvComment.text = "Comment"
            }
        }


        viewModel.addCommentStatus.observe(viewLifecycleOwner) {
            if (it != null &&it == true) {
                viewModel.getCommentsOfSong(currentSong.id)
                binding.edtComment.text = null
                Toast.makeText(requireContext(), viewModel.messageStatus, Toast.LENGTH_SHORT).show()
            }

            viewModel.addCommentStatus.postValue(null)
        }
        viewModel.deleteCommentStatus.observe(viewLifecycleOwner) {

            if (it != null &&it == true) {
                viewModel.getCommentsOfSong(currentSong.id)

            }
            viewModel.deleteCommentStatus.postValue(null)
        }

        viewModel.updateCommentStatus.observe(viewLifecycleOwner) {

            if (it != null &&it == true) {
                viewModel.getCommentsOfSong(currentSong.id)

            }
            viewModel.updateCommentStatus.postValue(null)
        }


    }

    private fun handleUI() {
        binding.rcyComment.adapter = commentAdapter
        binding.btnDown.setOnClickListener {
            dismiss()
        }

        binding.edtComment.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val str = binding.edtComment.text.toString().trim()
                if (str.isNotEmpty()) {
                    viewModel.addCommentToSong(str, currentSong.id)
                }
            }
            true
        }

    }

    private fun getData() {
        viewModel.getCommentsOfSong(currentSong.id)
    }

    override fun onCommentClicked(comment: Comment) {
    }

    override fun openMenu(comment: Comment) {
        Log.d("ABC","Clicked")
        val action = CommentFragmentDirections.actionCommentFragmentToCommentMenu(comment)
        findNavController().navigate(action)
    }





}