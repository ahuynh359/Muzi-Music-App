package com.ahuynh.muzimusicapp.ui.component.admin.song.detail_manage_song

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentManageSongDetailBinding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.ManageSongViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManageSongDetailFragment :
    BaseFragment<FragmentManageSongDetailBinding>(
        FragmentManageSongDetailBinding::inflate
    ) {

    companion object {
        const val TAG = "ManageSongDetailFragment"
    }
    private val selectedSingers = mutableListOf<Singer>()
    private val selectedTypes = mutableListOf<Type>()
    private var selectedAlbum: Album? = null
    private val viewModel by viewModels<ManageSongViewModel>({ requireActivity() })
    private lateinit var currentSong: Song
    private var fileChooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val file = FileHelper.from(requireContext(), uri)!!
            file.let {
                viewModel.changeAvatar(currentSong.id,it)
            }
        } else {
            Toast.makeText(requireContext(), "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }

    private var fileMp3Chooser: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { ur ->
            val file  = FileHelper.from(requireContext(), uri)
            file?.let {
                viewModel.uploadMusic(currentSong.id,it)

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSong = ManageSongDetailFragmentArgs.fromBundle(requireArguments()).song
        viewModel.getSongById(currentSong.id)
        viewModel.getAllAlbums()
        viewModel.getAllSingers()
        viewModel.getAllTypes()
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUI()
        observeData()


    }

    private fun observeData() {

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
                binding.pgLoadingAvatar.show()
            } else {
                binding.btnEdit.visibility = View.VISIBLE
                binding.imvAvatar.visibility = View.VISIBLE
                binding.pgLoadingAvatar.hide()
            }
        }

        viewModel.mp3File.observe(viewLifecycleOwner) {
            binding.edtFileMusic.text = it
        }

        viewModel.updateSongStatus.observe(viewLifecycleOwner){
            if(it == true){
                findNavController().popBackStack()
            }
            viewModel.updateSongStatus.postValue(null)
        }

    }


    private fun handleUI() {
        viewModel.song.observe(viewLifecycleOwner){song->
            binding.tvId.text = song.id.toString()
            binding.tvFileMusic.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(song.file))
                startActivity(intent)
            }
            currentSong.let {
                Glide
                    .with(binding.imvAvatar.context)
                    .load(song.avatar)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imvAvatar)

                binding.edtSongName.setText(song.name)
                binding.edtFileMusic.text = song.file
                binding.edtLyrics.setText(song.lyrics)
                binding.edtAlbum.text = song.album.name
                binding.edtChooseSinger.text = song.singers.joinToString { it.name }
                binding.edtChooseType.text = song.types.joinToString { it.name }

                binding.tvCreatedAt.text = song.createdAt
                binding.tvUpdatedAt.text = song.updatedAt



            }
        }


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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

        binding.edtFileMusic.setOnClickListener {
            try {
                fileMp3Chooser.launch("audio/mpeg")
                viewModel.getSongById(currentSong.id)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Install file manager", Toast.LENGTH_SHORT).show()
            }
        }

        binding.edtAlbum.setOnClickListener {
            showAlbumDialog()
        }

        binding.edtChooseType.setOnClickListener {
            showTypeDialog()
        }
        binding.edtChooseSinger.setOnClickListener {
            showSingerDialog()
        }

        binding.btnDone.setOnClickListener {
            val name = binding.edtSongName.text.toString().trim()
            val lyrics = binding.edtLyrics.text.toString().trim()
            viewModel.nameSong = name
            viewModel.lyricsSong = lyrics
            viewModel.updateSong(currentSong.id)

        }



    }

    private fun showAlbumDialog() {
        viewModel.albumList.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose an album")

            val albumNames = it.map { it.name }.toTypedArray()
            val checkedItem = it.indexOf(selectedAlbum)

            builder.setSingleChoiceItems(albumNames, checkedItem) { _, which ->
                selectedAlbum = it[which]
            }

            builder.setPositiveButton("OK") { dialog, _ ->
                binding.edtAlbum.text = selectedAlbum?.name
                viewModel.albumId = selectedAlbum?.id
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

    }

    private fun showSingerDialog() {
        viewModel.singerList.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose singers")

            val singerNames = it.map { it.name }.toTypedArray()
            val checkedItems = BooleanArray(it.size) { index ->
                selectedSingers.contains(it[index])
            }

            builder.setMultiChoiceItems(singerNames, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    viewModel.singerIds.add(it[which].id)
                    selectedSingers.add(it[which])
                } else {
                    viewModel.singerIds.remove(it[which].id)
                    selectedSingers.remove(it[which])
                }
            }

            builder.setPositiveButton("OK") { dialog, _ ->
                viewModel.singerName = selectedSingers.joinToString(", ") { it.name }
                binding.edtChooseSinger.text = selectedSingers.joinToString(", ") { it.name }
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }
    }

    private fun showTypeDialog() {
        viewModel.typeList.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose types")

            val typeNames = it.map { it.name }.toTypedArray()
            val checkedItems = BooleanArray(it.size) { index ->
                selectedTypes.contains(it[index])
            }

            builder.setMultiChoiceItems(typeNames, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    viewModel.typeIds.add(it[which].id)
                    selectedTypes.add(it[which])
                } else {
                    viewModel.typeIds.remove(it[which].id)
                    selectedTypes.remove(it[which])
                }
            }

            builder.setPositiveButton("OK") { dialog, _ ->
                viewModel.typeName = selectedTypes.joinToString(", ") { it.name }
                binding.edtChooseType.text = selectedTypes.joinToString(", ") { it.name }
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }
    }




}


