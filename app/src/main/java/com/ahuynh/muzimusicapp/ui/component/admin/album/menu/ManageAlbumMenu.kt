package com.ahuynh.muzimusicapp.ui.component.admin.album.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentManageAlbumMenuBinding
import com.ahuynh.muzimusicapp.databinding.FragmentMangeUserMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.admin.album.ManageAlbumViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.type.ManageTypeViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.type.Type.ManageTypeMenuArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAlbumMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageUserType"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentManageAlbumMenuBinding
    private val viewModel by viewModels<ManageAlbumViewModel>({ requireActivity() })
    private lateinit var currentAlbum: Album
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentAlbum = ManageAlbumMenuArgs.fromBundle(requireArguments()).album
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
        binding = FragmentManageAlbumMenuBinding.inflate(
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
                            viewModel.deleteAlbum(currentAlbum.id)

                            viewModel.deleteAlbumStatus.observe(viewLifecycleOwner) {
                                it?.let {
                                    if(it){
                                        dismiss()
                                        viewModel.getNewAlbums()
                                    }
                                    viewModel.mess?.let { mess ->
                                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                                    }

                                }
                                viewModel.deleteAlbumStatus.postValue(null)

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