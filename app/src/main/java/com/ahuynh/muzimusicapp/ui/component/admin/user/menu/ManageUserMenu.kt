package com.ahuynh.muzimusicapp.ui.component.admin.user.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentMangeUserMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUserMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageUserUser"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentMangeUserMenuBinding
    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private lateinit var currentUser: User
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUser = ManageUserMenuArgs.fromBundle(requireArguments()).user
        initData()
    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Delete User",
                R.drawable.ic_delete_comment,
                ItemMenuName.DELETE
            )
        )
        if(currentUser.locked) {
            itemMenuList.add(
                ItemMenu(
                    "Unlock User",
                    R.drawable.ic_unlock,
                    ItemMenuName.LOCK
                )
            )
        } else {
            itemMenuList.add(
                ItemMenu(
                    "Lock User",
                    R.drawable.ic_lock,
                    ItemMenuName.LOCK
                )
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangeUserMenuBinding.inflate(
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
                    title = "Confirm Delete User",
                    message = "Do you want to delete this User",
                    negativeButtonTitle = "CANCEL",
                    positiveButtonTitle = "DELETE",
                    callback = object : ConfirmDialog.ConfirmCallBack {
                        override fun negativeAction() {
                            dismiss()
                        }

                        override fun positiveAction() {
                            viewModel.deleteUser(currentUser.id)

                            viewModel.deleteUserStatus.observe(viewLifecycleOwner) {
                                it?.let {
                                    if(it){
                                        dismiss()
                                        viewModel.getAllUsers()
                                    }
                                    viewModel.mess?.let { mess ->
                                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                                    }

                                }
                                viewModel.deleteUserStatus.postValue(null)

                            }

                        }

                    }
                ).show()
            }
            ItemMenuName.LOCK->{
                viewModel.lockOrUnlockUser(currentUser.id)
                viewModel.lockOrUnlockStatus.observe(viewLifecycleOwner) {
                    it?.let {
                        if(it){
                            dismiss()
                            viewModel.getAllUsers()
                        }
                        viewModel.mess?.let { mess ->
                            Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                        }

                    }
                    viewModel.lockOrUnlockStatus.postValue(null)

                }
            }


            else -> {

            }
        }
    }


}