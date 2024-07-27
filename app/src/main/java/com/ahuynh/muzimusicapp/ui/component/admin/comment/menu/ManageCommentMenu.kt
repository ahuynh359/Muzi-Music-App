package com.ahuynh.muzimusicapp.ui.component.admin.comment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.databinding.FragmentCommentMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.admin.comment.ManageCommentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageCommentMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageUserType"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentCommentMenuBinding
    private val viewModel by viewModels<ManageCommentViewModel>({ requireActivity() })
    private lateinit var currentComment: Comment
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentComment = ManageCommentMenuArgs.fromBundle(requireArguments()).comment
    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Delete comment",
                R.drawable.ic_delete,
                ItemMenuName.DELETE
            )
        )
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


        handleUI()
        return binding.root
    }

    private fun handleUI() {
        binding.rcyMenu.adapter = menuAdapter
        menuAdapter.submitList(itemMenuList)

    }



    override fun onMenuClicked(menu: ItemMenu) {
        when(menu.type){
            ItemMenuName.DELETE ->{
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
                                it?.let {
                                    if(it){
                                        dismiss()
                                        viewModel.getAllComments()
                                    }
                                    viewModel.mess?.let { mess ->
                                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                                    }

                                }
                                viewModel.deleteCommentStatus.postValue(null)

                            }

                        }

                    }
                ).show()
            }


            else -> {

            }
        }
    }


}