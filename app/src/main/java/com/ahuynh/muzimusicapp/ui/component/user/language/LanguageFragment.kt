package com.ahuynh.muzimusicapp.ui.component.user.language

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.databinding.FragmentLanguageBinding
import com.ahuynh.muzimusicapp.ui.component.user.UserActivity
import com.ahuynh.muzimusicapp.utils.helper.RuntimeLocaleHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BottomSheetDialogFragment(), MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "LanguageFragment"
    }

    private val itemMenuList = ArrayList<ItemMenu>()
    private lateinit var binding: FragmentLanguageBinding
    private val viewModel by viewModels<LanguageViewModel>({ requireActivity() })
    private val menuAdapter = MenuAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeData()

    }

    private fun observeData() {
        viewModel.language.observe(viewLifecycleOwner) {
            (activity as? UserActivity)?.setLanguage(it)

        }
    }


    private fun setupUI() {
        binding.rcyMenu.adapter = menuAdapter
        itemMenuList.add(
            ItemMenu(getString(R.string.vi), R.drawable.ic_vn, ItemMenuName.VI)
        )

        itemMenuList.add(
            ItemMenu(getString(R.string.en), R.drawable.ic_us, ItemMenuName.US)
        )



        menuAdapter.submitList(itemMenuList)
    }


    override fun onMenuClicked(menu: ItemMenu) {
        when (menu.type) {
            ItemMenuName.VI -> {

                viewModel.changeLanguage("vi")
                dismiss()
            }

            ItemMenuName.US -> {
                viewModel.changeLanguage("en")
                dismiss()
            }



            else -> {
            }
        }
    }
}