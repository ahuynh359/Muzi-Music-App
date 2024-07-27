package com.ahuynh.muzimusicapp.ui.component.user.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.CommentAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentCommentBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.user.comment.menu.CommentMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : BaseDialogBottomSheetFragment(), CommentAdapter.OnCommentClicked
  {

    companion object {
        const val TAG = "CommentFragment"
    }

    private lateinit var currentSong: Song

    private lateinit var binding: FragmentCommentBinding
    private val viewModel by viewModels<CommentViewModel>({ requireActivity() })
    private val commentAdapter = CommentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSong = CommentFragmentArgs.fromBundle(requireArguments()).song

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observe()
    }

    private fun observe() {
        viewModel.commentList.observe(viewLifecycleOwner) {
            binding.rcyComment.visibility = View.VISIBLE
            if (it != null) {
                commentAdapter.submitList(it)
            }


        }

        viewModel.totalComments.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.tvNoComment.visibility = View.INVISIBLE
                binding.tvComment.text = getString(R.string.comment_count, it);
            } else {
                binding.tvNoComment.visibility = View.VISIBLE
                binding.tvComment.text = getString(R.string.comment)
            }
        }


        viewModel.addCommentStatus.observe(viewLifecycleOwner) {
            it?.let {
                binding.edtComment.text = null
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }

            viewModel.addCommentStatus.postValue(null)
        }
        viewModel.deleteCommentStatus.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }
            viewModel.deleteCommentStatus.postValue(null)
        }

        viewModel.updateCommentStatus.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }
            viewModel.updateCommentStatus.postValue(null)
        }

        viewModel.commentReply.observe(viewLifecycleOwner) {
            if(it != null){
                binding.edtComment.setText("@"+ it.user.username + " ")
                binding.tvName.text = it.user.username
                binding.reply.visibility  = View.VISIBLE
                binding.btnCancle.visibility = View.VISIBLE
            } else {
                binding.reply.visibility  = View.GONE
                binding.btnCancle.visibility = View.GONE
            }
        }

        viewModel.replyCommentStatus.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.commentReply.postValue(null)
                binding.edtComment.text = null
                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
                getData()
            }

            viewModel.replyCommentStatus.postValue(null)
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

        binding.btnSend.setOnClickListener {
            val commentText = binding.edtComment.text.toString().trim()

            if (commentText.isNotEmpty()) {
                viewModel.commentReply.value?.let { reply ->
                    val commentParentId = reply.commentParentId ?: reply.id
                    viewModel.addReply(currentSong.id, commentParentId, commentText)
                } ?: run {
                    viewModel.addCommentToSong(commentText, currentSong.id)
                }
            }
        }
        binding.btnCancle.setOnClickListener {
            viewModel.commentReply.postValue(null)
            binding.edtComment.text = null
        }



    }

    private fun getData() {
        viewModel.getCommentsOfSong(currentSong.id)
    }


    override fun openMenu(comment: Comment) {
        val action = CommentFragmentDirections.actionCommentFragmentToCommentMenu(comment)
        findNavController().navigate(action)
    }

    override fun replyComment(comment: Comment) {
       viewModel.commentReply.postValue(comment)
    }



}