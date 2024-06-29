package com.ahuynh.muzimusicapp.ui.component.main.profile

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SettingAdapter
import com.ahuynh.muzimusicapp.adapter.UserListAdapter
import com.ahuynh.muzimusicapp.data.model.SettingItem
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.UserList
import com.ahuynh.muzimusicapp.data.model.UserListName
import com.ahuynh.muzimusicapp.databinding.FragmentProfileBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSettingBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate),
    UserListAdapter.OnUserListAdapterClicked {
    companion object {
        const val TAG = "ProfileFragment"
    }

    private val userListAdapter = UserListAdapter(this)
    private val userList : ArrayList<UserList> = arrayListOf()
    private val viewModel by viewModels<ProfileViewModel>()
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        val file: File? = FileHelper.from(requireContext(), uri!!)
        file?.let {
            viewModel.changeAvatar(
                file
            )

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observeData()

    }

    private fun observeData() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            Glide
                .with(binding.imvAvatar.context)
                .load(it.avatar)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAvatar);


        }

        viewModel.avatar.observe(viewLifecycleOwner) {
            Glide
                .with(binding.imvAvatar.context)
                .load(it)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imvAvatar);
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnEdit.visibility = View.INVISIBLE
                binding.imvAvatar.visibility = View.INVISIBLE
                binding.pgLoading.show()
            } else {
                binding.btnEdit.visibility = View.VISIBLE
                binding.imvAvatar.visibility = View.VISIBLE
                binding.pgLoading.hide()
            }
        }

    }

    private fun initData(){
        userList.add(UserList(R.drawable.big_song,UserListName.PLAYLIST))
        userList.add(UserList(R.drawable.big_song,UserListName.LOVESONG))
        userList.add(UserList(R.drawable.big_song,UserListName.LOVESINGER))
    }

    private fun handleUI() {
        binding.rcyUserList.adapter = userListAdapter
        userListAdapter.submitList(userList)
        binding.btnEditPassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)
        }

        binding.btnSetting.setOnClickListener {
            navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
        }




        binding.btnEdit.setOnClickListener {
            try {
                fileChooser.launch("image/*")
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng cài đặt File Manager",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onUserListAdapterClicked(str: UserList) {
       when (str.name) {
            UserListName.PLAYLIST -> {
                navigate(ProfileFragmentDirections.actionProfileFragmentToPlaylistFragment())
            }
            UserListName.LOVESONG -> "Love Song"
            UserListName.LOVESINGER -> "Love Singer"
        }

    }
    private fun navigate(nav : NavDirections){
        findNavController().navigate(nav)
    }

}