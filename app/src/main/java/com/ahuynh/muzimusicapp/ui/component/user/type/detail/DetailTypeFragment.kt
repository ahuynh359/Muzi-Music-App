package com.ahuynh.muzimusicapp.ui.component.user.type.detail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.BaseDialogBottomSheetFragment
import com.ahuynh.muzimusicapp.ui.component.user.type.TypeViewModel
import com.ahuynh.muzimusicapp.ui.component.player.PlayerActivity
import com.ahuynh.muzimusicapp.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTypeFragment :
    BaseDialogBottomSheetFragment(),
    SongAdapter.OnSongClicked {

    companion object {
        const val TAG = "DetailTypeFragment"
    }

    private var songAdapter = SongAdapter(this)
    private val viewModel by viewModels<TypeViewModel>()
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
        binding = FragmentDetailTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleUI()
        observe()

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


        binding.rcySongs.adapter = songAdapter
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

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.tvNoSongs.visibility = View.GONE
                    songAdapter.submitList(songOfType)
                } else {
                    filterSongs(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.btnPlay.setOnClickListener {
            startActivity(Intent(requireContext(), PlayerActivity::class.java))
            Utils.sendNewMusic(
                requireActivity(),
                MusicService.ACTION_PLAY,
                songOfType[0], songOfType
            )
        }


    }

    private fun filterSongs(query: String) {
        val filteredList = songOfType.filter { song ->
            song.name.contains(query, ignoreCase = true)
        }
        if (filteredList.isEmpty()) {
            binding.tvNoSongs.visibility = View.VISIBLE
        } else
            binding.tvNoSongs.visibility = View.GONE
        songAdapter.submitList(filteredList)
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