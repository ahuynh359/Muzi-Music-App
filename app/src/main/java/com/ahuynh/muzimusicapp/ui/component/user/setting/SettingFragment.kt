package com.ahuynh.muzimusicapp.ui.component.user.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SettingAdapter
import com.ahuynh.muzimusicapp.data.model.SettingItem
import com.ahuynh.muzimusicapp.data.model.SettingName
import com.ahuynh.muzimusicapp.databinding.FragmentSettingBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.auth.AuthActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate),SettingAdapter.OnSettingAdapterClicked {
    companion object {
        const val TAG = "SettingFragment"
    }

    private val settingList = ArrayList<SettingItem>()
    private val settingAdapter = SettingAdapter(this)



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
        viewModel.currentUser.observe(viewLifecycleOwner){
            Glide
                .with(binding.imvAvatar.context)
                .load(it.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAvatar);

            binding.tvName.text = it.username

        }
    }

    private fun initSettingItem(){
        settingList.add(SettingItem(SettingName.LANGUAGE))
        settingList.add(SettingItem(SettingName.THEME))
        settingList.add(SettingItem(SettingName.SECURITY))
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

    override fun onSettingClicked(setting: SettingItem) {
        if(setting.name == SettingName.LANGUAGE){

        } else if(setting.name == SettingName.THEME){

        }
    }
}