package com.ahuynh.muzimusicapp.ui.component.admin.user.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.databinding.FragmentMangeUserMenuBinding
import com.ahuynh.muzimusicapp.ui.component.admin.user.ManageUserViewModel
import com.ahuynh.muzimusicapp.ui.component.admin.user.detail_manage_user.ManageUserDetailFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUserMenu : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "ManageUserMenu"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentMangeUserMenuBinding
    private val viewModel by viewModels<ManageUserViewModel>({ requireActivity() })
    private lateinit var currentUser: User
    private val menuAdapter = MenuAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentUser = ManageUserDetailFragmentArgs.fromBundle(requireArguments()).user
    }

    private fun initData() {



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
        binding.tvUser.text = currentUser.username


    }

    override fun onMenuClicked(menu: ItemMenu) {

    }


}