package com.ahuynh.muzimusicapp.ui.upload

import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val songRepository: SongRepository
) : BaseViewModel(){
    fun addSong() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            uploadSongAndImage()
        }
        registerEventParentJobFinish()
    }

    private fun uploadSongAndImage() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch { songRepository.addImageToFirebaseStorage(imageFile!!) }
        registerEventParentJobFinish()
    }

    var songName = ""
    var singerName = ""
    var lyrics = ""
    var songFile: File? = null
    var imageFile: File? = null

}