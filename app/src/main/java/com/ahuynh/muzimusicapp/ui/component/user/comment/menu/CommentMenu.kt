package com.ahuynh.muzimusicapp.ui.component.user.comment.menu

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.databinding.FragmentCommentMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.user.comment.CommentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {

    companion object {
        const val TAG = "CommentMenu"
    }

    interface CommentMenuListener {
        fun onReplySelected(comment: Comment)
    }





    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentCommentMenuBinding
    private val viewModel by viewModels<CommentViewModel>({ requireActivity() })
    private lateinit var currentComment: Comment
    private val menuAdapter = MenuAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentMenuBinding.inflate(inflater, container, false)
        currentComment = CommentMenuArgs.fromBundle(requireArguments()).comment
        initData()
        setupUI()
        observeViewModel()
        return binding.root
    }

    private fun initData() {
        itemMenuList.apply {
            add(ItemMenu("Copy", R.drawable.ic_copy, ItemMenuName.COPY))
        }
        viewModel.currentUserId.observe(viewLifecycleOwner) { userId ->
            if (userId != null && userId == currentComment.user.id) {
                itemMenuList.apply {
                    add(ItemMenu("Edit", R.drawable.ic_edit_comment, ItemMenuName.EDIT))
                    add(ItemMenu("Delete", R.drawable.ic_delete, ItemMenuName.DELETE))
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.updateCommentStatus.observe(viewLifecycleOwner) {
            if (it == true) dismiss()
        }
    }

    private fun setupUI() {
        binding.apply {
            rcyMenu.adapter = menuAdapter
            menuAdapter.submitList(itemMenuList)
            tvComment.text = getString(R.string.comment_of, currentComment.user.username)
        }
    }

    override fun onMenuClicked(menu: ItemMenu) {
        when (menu.type) {

            ItemMenuName.COPY -> {
                copyTextToClipboard(currentComment.content)
                Toast.makeText(requireContext(), "Copy to clipboard", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            ItemMenuName.EDIT -> {
                val action = CommentMenuDirections.actionCommentMenuToEditCommentFragment(currentComment)
                findNavController().navigate(action)
            }
            ItemMenuName.DELETE -> {
                ConfirmDialog(
                    requireContext(),
                    title = "Confirm Delete Comment",
                    message = "Do you want to delete this comment",
                    negativeButtonTitle = "CANCEL",
                    positiveButtonTitle = "DELETE",
                    callback = object : ConfirmDialog.ConfirmCallBack {
                        override fun negativeAction() {
                            dismiss()
                        }

                        override fun positiveAction() {
                            viewModel.deleteComment(currentComment.id)
                            viewModel.deleteCommentStatus.observe(viewLifecycleOwner) {
                                Toast.makeText(requireContext(), viewModel.mess, Toast.LENGTH_SHORT).show()
                                if (it == true) dismiss()
                            }
                        }
                    }
                ).show()
            }
            else -> {
                // Handle other actions
            }
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }
}