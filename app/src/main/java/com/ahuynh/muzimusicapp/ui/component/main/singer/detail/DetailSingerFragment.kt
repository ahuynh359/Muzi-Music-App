package com.ahuynh.muzimusicapp.ui.component.main.singer.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.adapter.SongAdapter
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.databinding.FragmentDetailSingerBinding
import com.ahuynh.muzimusicapp.service.MusicService
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.main.song.menu.SongMenu
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSingerFragment : BaseFragment<FragmentDetailSingerBinding>(FragmentDetailSingerBinding::inflate)
    ,SongAdapter.OnNewSongClicked {

    companion object {
        const val TAG = "DetailSingerFragment"
    }
    private  val songAdapter = SongAdapter(this)
    private val viewModel by viewModels<DetailSingerViewModel>()
    private lateinit var songOfSinger: ArrayList<Song>
    private lateinit var currentSinger: Singer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSinger = DetailSingerFragmentArgs.fromBundle(requireArguments()).singer

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        handleUI()
        observe()

    }

    private fun getData() {
        viewModel.getSongOfSinger(currentSinger.id)
        viewModel.isUserLoveSinger(currentSinger.id)
    }

    private fun observe() {

        viewModel.songOfSinger.observe(viewLifecycleOwner) {

            songAdapter.submitList(it)
            songOfSinger = it as ArrayList<Song>
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

        viewModel.loveSinger.observe(viewLifecycleOwner){
            if(it){
                binding.btnFollow.text = "Unfollow"
            } else {
                binding.btnFollow.text = "Follow"
            }
        }


    }



    private fun handleUI() {

        binding.rcySongs.adapter = songAdapter
        Glide
            .with(binding.imvSinger.context)
            .load(currentSinger.avatar)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imvSinger)
        binding.tvSinger.text = currentSinger.name



        binding.btnBack.setOnClickListener {
           findNavController().popBackStack()
        }


        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendNewMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOfSinger[0], songOfSinger
            )
        }

        binding.btnFollow.setOnClickListener {
            viewModel.loveOrUnloveSinger(currentSinger.id)
            if(viewModel.mess != null){
                Toast.makeText(requireContext(),viewModel.mess,Toast.LENGTH_SHORT).show()
            }
        }




    }

    override fun onSongClicked(song: Song) {
        startActivity(Intent(requireContext(), PlayerActivity::class.java))
        Utils.sendNewMusic(
            requireActivity(),
            MusicService.ACTION_PLAY,
            song, songOfSinger
        )
    }

    override fun openMenu(song: Song) {
        SongMenu().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.SONG,song)
            }
        }.show(requireActivity().supportFragmentManager,null)
    }



}