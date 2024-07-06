package com.ahuynh.muzimusicapp.ui.component.user.singer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SingerAdapter
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.databinding.FragmentSingerBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingerFragment : BaseFragment<FragmentSingerBinding>(FragmentSingerBinding::inflate)
    , SingerAdapter.OnSingerClicked {

    companion object {
        const val TAG = "SingerFragment"
    }
    private  val singerAdapter = SingerAdapter(this)
    private val viewModel by viewModels<SingerViewModel>()
    private lateinit var singerList: ArrayList<Singer>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getAllSinger()
    }

    private fun observe() {

        viewModel.singerList.observe(viewLifecycleOwner) {
            singerAdapter.submitList(it)
            singerList = it as ArrayList<Singer>
            binding.rcySinger.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.tvNoSinger.visibility = View.VISIBLE
            } else {
                binding.tvNoSinger.visibility = View.GONE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }


    }



    private fun handleUI() {

        binding.rcySinger.adapter = singerAdapter


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }




    }


    override fun onSingerClicked(singer: Singer) {

    }


}