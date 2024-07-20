package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.step

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ahuynh.muzimusicapp.R
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.databinding.FragmentStep2Binding
import com.ahuynh.muzimusicapp.databinding.FragmentStep3Binding
import com.ahuynh.muzimusicapp.ui.base.fragment.BaseFragment
import com.ahuynh.muzimusicapp.ui.component.admin.song.add_song.UploadViewModel
import com.ahuynh.muzimusicapp.utils.helper.FileHelper
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class Step3Fragment : BaseFragment<FragmentStep3Binding>(FragmentStep3Binding::inflate) {
    private val viewModel by viewModels<UploadViewModel>({ requireActivity() })

    private val selectedSingers = mutableListOf<Singer>()
    private val selectedTypes = mutableListOf<Type>()
    private var selectedAlbum: Album? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllAlbums()
        viewModel.getAllSingers()
        viewModel.getAllTypes()
    }


    private fun handleUI() {
        binding.edtAlbum.setOnClickListener {
            showAlbumDialog()
        }
        binding.edtChooseSinger.setOnClickListener {
            showSingerDialog()
        }
        binding.edtChooseType.setOnClickListener {
            showTypeDialog()
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

    override fun onStart() {
        super.onStart()
        binding.edtAlbum.text = viewModel.albumName
        binding.edtChooseSinger.text = viewModel.singerName
        binding.edtChooseType.text = viewModel.typeName
    }

    override fun onStop() {
        super.onStop()
        viewModel.albumName = binding.edtAlbum.text.toString().trim()

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