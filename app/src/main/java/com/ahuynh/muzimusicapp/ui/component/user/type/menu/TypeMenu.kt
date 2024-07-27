package com.ahuynh.muzimusicapp.ui.component.user.type.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.TypeAdapter
import com.ahuynh.muzimusicapp.adapter.TypeViewType
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentTypeMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeMenu : BottomSheetDialogFragment(), TypeAdapter.OnTypeClicked {
    companion object {
        const val TAG = "TypeMenu"
    }

    private val typeAdapter = TypeAdapter(this, TypeViewType.FULL,false)
    private lateinit var binding: FragmentTypeMenuBinding
    private lateinit var currentSong: Song
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        currentSong = TypeMenuArgs.fromBundle(requireArguments()).song
    }

    private fun initData() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTypeMenuBinding.inflate(
            inflater,
            container,
            false
        )


        handleUI()

        return binding.root
    }





    private fun handleUI() {
        binding.rcyType.adapter = typeAdapter
        typeAdapter.submitList(currentSong.types)

    }


    override fun onTypeClicked(type: Type) {
        val action = TypeMenuDirections.actionTypeMenuToDetailTypeFragment(type)
        findNavController().navigate(action)
    }

    override fun onMoreClicked(type: Type) {
    }


}