package com.ahuynh.muzimusicapp.ui.component.admin.song.menu

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
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentManageSongMenuBinding
import com.ahuynh.muzimusicapp.ui.base.dialog.ConfirmDialog
import com.ahuynh.muzimusicapp.ui.component.admin.song.ManageSongViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageSongMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageSongMenu"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentManageSongMenuBinding
    private val viewModel by viewModels<ManageSongViewModel>({ requireActivity() })
    private lateinit var currentSong: Song
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSong = ManageSongMenuArgs.fromBundle(requireArguments()).song
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        handleUI()

    }

    private fun initData() {
        itemMenuList.add(
            ItemMenu(
                "Delete Song",
                R.drawable.ic_delete,
                ItemMenuName.DELETE
            )
        )

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageSongMenuBinding.inflate(
            inflater,
            container,
            false
        )

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
                    title = "Confirm Delete Song",
                    message = "Do you want to delete this Song",
                    negativeButtonTitle = "CANCEL",
                    positiveButtonTitle = "DELETE",
                    callback = object : ConfirmDialog.ConfirmCallBack {
                        override fun negativeAction() {
                            dismiss()
                        }

                        override fun positiveAction() {
                            viewModel.deleteSong(currentSong.id)

                            viewModel.deleteSongStatus.observe(viewLifecycleOwner) {
                                it?.let {
                                    if(it){
                                        dismiss()
                                        viewModel.getAllSongs()
                                    }
                                    viewModel.mess?.let { mess ->
                                        Toast.makeText(requireContext(), mess, Toast.LENGTH_LONG).show()
                                    }

                                }
                                viewModel.deleteSongStatus.postValue(null)

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