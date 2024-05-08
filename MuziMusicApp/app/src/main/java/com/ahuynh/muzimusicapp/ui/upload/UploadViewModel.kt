package com.ahuynh.muzimusicapp.ui.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.SongPost
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val songRepository: SongRepository

) : BaseViewModel(){
    var addSongStatus = MutableLiveData<Boolean>()
    var addImage = MutableLiveData<String>()
    var addFileMp3 = MutableLiveData<String>()
    lateinit var song: SongPost
    fun addImageAndFile() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val imageDeferred = async { songRepository.addImageToFirebaseStorage(imageFile!!) }
            val fileDeferred = async { songRepository.addFileToFirebaseStorage(songFile!!) }

            val imageResponse = imageDeferred.await()
            val fileResponse = fileDeferred.await()

            if (imageResponse is Response.Success && fileResponse is Response.Success) {
                addImage.postValue(imageResponse.data)
                addFileMp3.postValue(fileResponse.data)

            } else {

                if (imageResponse is Response.Failure) {
                    message.postValue(imageResponse.errorMessage)
                }
                if (fileResponse is Response.Failure) {
                    message.postValue(fileResponse.errorMessage)
                }
            }


        }
        registerEventParentJobFinish()

    }

    fun addSong(song: SongPost) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val response = songRepository.addSong(song)
            if (response is Response.Success) {
                addSongStatus.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }


        }
        registerEventParentJobFinish()
    }


    var songName = ""
    var singerName = ""
    var lyrics = ""
    var songFile: File? = null
    var imageFile: File? = null

}