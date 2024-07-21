package com.ahuynh.muzimusicapp.ui.component.admin.comment.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.CommentAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.databinding.FragmentSearchManageCommentBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.comment.ManageCommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchManageCommentFragment : BaseFragment<FragmentSearchManageCommentBinding>(
    FragmentSearchManageCommentBinding::inflate
),
    CommentAdapter.OnCommentClicked {

    private val viewModel by viewModels<ManageCommentViewModel>({ requireActivity() })
    private val commentAdapter = CommentAdapter(this)
    private var commentList: ArrayList<Comment> = arrayListOf()

    companion object {
        const val TAG = "SearchManageCommentFragment"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllComments()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observeData()


    }

    private fun handleUI() {
        binding.rcyComment.adapter = commentAdapter
        binding.edtSearch.clearFocus()
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    commentAdapter.submitList(commentList)

                } else
                    performSearch(newText)
                return true
            }
        })

        binding.tvCancle.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun performSearch(query: String) {
        val searchCommentList = mutableListOf<Comment>()
        for (s in commentList) {
            if (s.content.lowercase().contains(query.lowercase())
            ) {
                searchCommentList.add(s)
            }
        }
        if (searchCommentList.isEmpty()) {
            commentAdapter.submitList(arrayListOf())
            binding.tvNoCommment.visibility = View.VISIBLE
        } else {
            binding.tvNoCommment.visibility = View.INVISIBLE
            commentAdapter.submitList(searchCommentList)
        }
    }

    private fun observeData() {
        viewModel.commentList.observe(viewLifecycleOwner) {
            binding.rcyComment.visibility = View.VISIBLE
            if (it != null) {
                commentList = it as ArrayList<Comment>
                commentAdapter.submitList(it)
            }


        }

    }




    override fun onReplyComment(comment: Comment) {
    }

    override fun onHeartComment(comment: Comment) {
    }

    override fun openMenu(comment: Comment) {

    }


}