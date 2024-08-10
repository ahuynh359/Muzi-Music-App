package com.ahuynh.muzimusicapp.ui.component.admin.comment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.CommentAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.databinding.FragmentManageCommentBinding
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageCommentFragment :
    BaseFragment<FragmentManageCommentBinding>(FragmentManageCommentBinding::inflate),
    CommentAdapter.OnCommentClicked, SortBottomSheetFragment.SortOptionListener {

    private val viewModel by viewModels<ManageCommentViewModel>({ requireActivity() })

    companion object {
        const val TAG = "ManageCommentFragment"
    }

    private val commentAdapter = CommentAdapter(this)

    private var commentList: ArrayList<Comment> = arrayListOf()


    override fun onResume() {
        super.onResume()
        viewModel.getAllComments()
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
                commentList = it as ArrayList<Comment>
                commentAdapter.submitList(it)
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.INVISIBLE


        }
        viewModel.sortComment.observe(viewLifecycleOwner) {
            binding.btnSort.text = it.name
            viewModel.getAllComments()
        }


    }


    private fun handleUI() {
        binding.rcyComment.adapter = commentAdapter


        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoComment.visibility = View.GONE
                    commentAdapter.submitList(commentList)
                } else {
                    filterComment(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })




        binding.btnSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment()
            sortBottomSheet.listener = this
            sortBottomSheet.show(parentFragmentManager, null)
        }


    }

    private fun filterComment(query: String) {
        val filteredList = commentList.filter { comment ->
            comment.content.contains(query, ignoreCase = true) || comment.user.username.contains(query, ignoreCase = true)
        }
        binding.tvNoComment.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE
        commentAdapter.submitList(filteredList)

    }


    override fun openMenu(comment: Comment) {
        val action =
            ManageCommentFragmentDirections.actionManageCommentFragmentToManageCommentMenu(comment)
        findNavController().navigate(action)
    }

    override fun replyComment(comment: Comment) {

    }


    override fun onSortOptionSelected(name: SortName) {
        when (name) {
            SortName.NEW -> {
                viewModel.setSortComment(SortName.NEW)
            }

            SortName.OLD -> {
                viewModel.setSortComment(SortName.OLD)
            }

            SortName.A_Z -> {
                viewModel.setSortComment(SortName.A_Z)

            }

            SortName.Z_A -> {
                viewModel.setSortComment(SortName.Z_A)
            }

            else -> {
                viewModel.setSortComment(SortName.NEW)
            }


        }
    }


}
