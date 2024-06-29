package com.ahuynh.muzimusicapp.ui.component.main.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.PlaylistAdapter
import com.ahuynh.muzimusicapp.databinding.FragmentPlaylistBinding
import com.ahuynh.muzimusicapp.ui.base.BaseDialogFragment
import com.ahuynh.muzimusicapp.ui.component.main.playlist.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BaseDialogFragment(){

    companion object {
        const val TAG = "SortFragment"
    }

    private lateinit var binding: FragmentPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getData()
//        handleUI()
//        observe()

    }
}