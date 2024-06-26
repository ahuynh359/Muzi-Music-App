package com.ahuynh.muzimusicapp.ui.component.activity.main.search.singer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentSingerSearchBinding
import com.ahuynh.muzimusicapp.ui.component.activity.main.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingerSearchFragment : Fragment(), SingerAdapter.OnSingerClicked {

    private lateinit var binding: FragmentSingerSearchBinding
    private val viewModel by viewModels<SearchViewModel>({ requireActivity() })

    private val singerAdapter = SingerAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingerSearchBinding.inflate(inflater, container, false)

        handleUI()
        observeData()


        return binding.root
    }

    private fun handleUI() {
        binding.rcySinger.adapter = singerAdapter

    }

    private fun observeData() {

        viewModel.singers.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoResult.visibility = View.VISIBLE
                binding.rcySinger.visibility = View.GONE
            } else {
                singerAdapter.submitList(it)

                binding.tvNoResult.visibility = View.GONE
                binding.rcySinger.visibility = View.VISIBLE
            }
        }
    }


    override fun onSingerClicked(singer: Singer) {

    }

}