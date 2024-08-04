package com.ahuynh.muzimusicapp.ui.component.user.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.MenuAdapter
import com.ahuynh.muzimusicapp.data.model.ItemMenu
import com.ahuynh.muzimusicapp.data.model.ItemMenuName
import com.ahuynh.muzimusicapp.databinding.FragmentSettingBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import com.ahuynh.muzimusicapp.utils.Utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate),
    MenuAdapter.OnItemMenuAdapterClicked {
    companion object {
        const val TAG = "SettingFragment"
    }

    private val settingList = ArrayList<ItemMenu>()
    private val settingAdapter = MenuAdapter(this)


    private val viewModel by viewModels<SettingViewModel>({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettingItem()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observeData()


    }

    private fun observeData() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            binding.imvAvatar.loadImage(it.avatar)
            binding.tvName.text = it.username

        }
    }

    private fun initSettingItem() {
        settingList.add(
            ItemMenu(
                getString(R.string.language),
                R.drawable.ic_language,
                ItemMenuName.LANGUAGE
            )
        )
    }


    private fun handleUI() {
        binding.btnLogOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        binding.rcySetting.adapter = settingAdapter
        settingAdapter.submitList(settingList)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


//        binding.viewProfile.setOnClickListener {
//            val action = SettingFragmentDirections.actionSettingFragmentToProfileFragment()
//            findNavController().navigate(action)
//        }


    }

    override fun onMenuClicked(menu: ItemMenu) {
        when (menu.type) {
            ItemMenuName.LANGUAGE -> {
                val action = SettingFragmentDirections.actionSettingFragmentToLanguageFragment()
                findNavController().navigate(action)
            }


            else -> {

            }
        }
    }


}