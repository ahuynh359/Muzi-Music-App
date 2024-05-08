package com.ahuynh.muzimusicapp.ui.upload

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadVIewModel @Inject constructor(
    private val songRepository: SongRepository
) : BaseViewModel(){

    var songEdit : Song?= null

    fun isFormAdd() : Boolean{
        return songEdit == null
    }
}