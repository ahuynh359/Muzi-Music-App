package com.ahuynh.muzimusicapp.ui.component.main.type

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentDetailTypeBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.BaseDialogFragment
import com.ahuynh.muzimusicapp.ui.component.main.home.HomeViewModel
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTypeFragment :
    BaseDialogFragment(),
    SongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "DetailTypeFragment"
    }

    private lateinit var songAdapter: SongAdapter
    private val viewModel by viewModels<HomeViewModel>({ requireActivity() })
    private lateinit var songOfType: ArrayList<Song>
    private lateinit var currentType: Type
    private lateinit var binding: FragmentDetailTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentType = DetailTypeFragmentArgs.fromBundle(requireArguments()).type

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTypeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songAdapter = SongAdapter(this)
        binding.rcySongs.adapter = songAdapter

        handleUI()
        observe()
        getData()
    }

    private fun getData() {
        viewModel.getSongOfType(currentType.id)
    }

    private fun observe() {

        viewModel.songOfType.observe(viewLifecycleOwner) {

            songAdapter.submitList(it)
            songOfType = it as ArrayList<Song>
            binding.rcySongs.visibility = View.VISIBLE
            if (it.isEmpty()) {
                binding.tvNoSongs.visibility = View.VISIBLE
                binding.btnPlay.visibility = View.INVISIBLE
            } else {
                binding.btnPlay.visibility = View.VISIBLE
                binding.tvNoSongs.visibility = View.GONE
            }
            binding.shimmer.stopShimmer()
            binding.shimmer.visibility = View.GONE
        }


    }



    private fun handleUI() {
        Glide
            .with(binding.imvType.context)
            .load(currentType.avatar)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imvType)
        binding.tvTypeName.text = currentType.name



        binding.btnBack.setOnClickListener {
            dismiss()
        }


        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendNewMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOfType[0], songOfType
            )
        }




    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfType
        )
    }

    override fun openMenu(song: Song) {
        Toast.makeText(requireContext(), "ABC", Toast.LENGTH_LONG).show()
    }




}