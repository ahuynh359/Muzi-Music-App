package com.ahuynh.muzimusicapp.ui.component.admin.type.Type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentMangeUserMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.user.detail_manage_user.ManageUserDetailFragmentArgs
import com.ahuynh.muzimusicapp.ui.component.user.song.add_song_to_playlist_bottom_sheet.AddSongToPlaylistBottomSheet
import com.ahuynh.muzimusicapp.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageTypeMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageUserType"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentMangeUserMenuBinding
    private val viewModel by viewModels<ManageTypeViewModel>({ requireActivity() })
    private lateinit var currentType: Type
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentType = ManageTypeMenuArgs.fromBundle(requireArguments()).type
    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Delete type",
                R.drawable.ic_delete_comment,
                ItemMenuName.DELETE
            )
        )
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
                    title = "Confirm Delete Type",
                    message = "Do you want to delete this type",
                    negativeButtonTitle = "CANCEL",
                    positiveButtonTitle = "DELETE",
                    callback = object : ConfirmDialog.ConfirmCallBack {
                        override fun negativeAction() {
                            dismiss()
                        }

                        override fun positiveAction() {
                            viewModel.deleteType(currentType.id)

                            viewModel.deleteTypeStatus.observe(viewLifecycleOwner) {
                                it?.let {
                                    if(it){
                                        dismiss()
                                        viewModel.getAllType()
                                    }
                                    viewModel.mess?.let { mess ->
                                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                                    }

                                }
                                viewModel.deleteTypeStatus.postValue(null)

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