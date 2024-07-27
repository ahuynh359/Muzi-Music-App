package com.ahuynh.muzimusicapp.ui.component.player.sleep

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.adapter.SingerViewType
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentSingerMenuBinding
import com.ahuynh.muzimusicapp.databinding.FragmentSleepDialogBinding
import com.ahuynh.muzimusicapp.service.BroadcastService
import com.ahuynh.muzimusicapp.service.BroadcastService.Companion.DURATION
import com.ahuynh.muzimusicapp.ui.component.user.singer.menu.SingerMenuArgs
import com.ahuynh.muzimusicapp.ui.component.user.singer.menu.SingerMenuDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SleepDialog : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "SleepDialog"
    }

    private lateinit var binding: FragmentSleepDialogBinding
    private var time = longArrayOf(10000, 15000, 20000, 60000)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSleepDialogBinding.inflate(
            inflater, container, false
        )


        handleUI()

        return binding.root
    }


    private fun handleUI() {
        binding.apply {
            tv10.setOnClickListener {
                startTimer(time[0])
            }
            tv15.setOnClickListener {
                startTimer(time[1])
            }
            tv20.setOnClickListener {
                startTimer(time[2])
            }
            tv60.setOnClickListener {
                startTimer(time[3])
            }
        }

    }

    private fun startTimer(timer: Long) {
        val intent = Intent(requireContext(), BroadcastService::class.java)
        intent.putExtra(DURATION, timer)
        requireActivity().startService(intent)
        dismiss()
        Toast.makeText(requireContext(), "${timer / 1000} has been set", Toast.LENGTH_SHORT).show()
    }


}