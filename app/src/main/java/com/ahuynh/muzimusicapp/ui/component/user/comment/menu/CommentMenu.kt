package com.ahuynh.muzimusicapp.ui.component.user.comment.menu
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
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



    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentCommentMenuBinding
    private val viewModel by viewModels<CommentViewModel>({requireActivity()})

    private lateinit var currentComment: Comment
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Reply",
                R.drawable.ic_comment,
                ItemMenuName.REPLY
            )
        )

        itemMenuList.add(
            ItemMenu(
                "Copy",
                R.drawable.ic_copy,
                ItemMenuName.COPY
            )
        )
        viewModel.currentUserId.observe(viewLifecycleOwner){
            if(it != null && it == currentComment.user.id){
                itemMenuList.add(
                    ItemMenu(
                        "Edit",
                        R.drawable.ic_edit_comment,
                        ItemMenuName.EDIT
                    )
                )

                itemMenuList.add(
                    ItemMenu(
                        "Delete",
                        R.drawable.ic_delete_comment,
                        ItemMenuName.DELETE
                    )
                )
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentMenuBinding.inflate(
            inflater,
            container,
            false
        )
        currentComment = CommentMenuArgs.fromBundle(requireArguments()).comment
        initData()
        handleUI()
        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.updateCommentStatus.observe(viewLifecycleOwner) {

            if (it != null &&it == true) {
              dismiss()

            }
        }
    }

    private fun handleUI() {
        binding.rcyMenu.adapter = menuAdapter
        menuAdapter.submitList(itemMenuList)


        binding.tvComment.text = "Comment of " + currentComment.user.username



    }

    override fun onMenuClicked(menu: ItemMenu) {
        when(menu.type){
            ItemMenuName.REPLY ->{

            }
            ItemMenuName.COPY ->{
                copyTextToClipboard(currentComment.content)
                Toast.makeText(requireContext(),"Copy to clipboard",Toast.LENGTH_SHORT).show()
                dismiss()

            }
            ItemMenuName.EDIT->{
                val action = CommentMenuDirections.actionCommentMenuToEditCommentFragment(currentComment)
                findNavController().navigate(action)
            }
            ItemMenuName.DELETE->{
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
                            viewModel.deleteCommentStatus.observe(viewLifecycleOwner){
                                if(it != null && it ==  true){
                                    Toast.makeText(requireContext(), viewModel.messageStatus, Toast.LENGTH_SHORT).show()
                                    dismiss()
                                } else if(it != null && it == false){
                                    Toast.makeText(requireContext(), viewModel.messageStatus, Toast.LENGTH_SHORT).show()
                                }
                            }

                        }

                    }
                ).show()
            }

           else ->{

           }
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

    }




}