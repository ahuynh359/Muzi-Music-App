package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: SongRepository,
) : BaseViewModel() {
    var songName = ""
    var lyrics = ""
    var songFile: File? = null
    var imageFile: File? = null
    lateinit var albumId : List<Long>
    lateinit var singerId : List<Long>
    lateinit var typeId : List<Long>
    var addImage = MutableLiveData<String>()
    var addFileMp3 = MutableLiveData<String>()
    var addSongStatus = MutableLiveData<Boolean>()



//    fun addSong() {
//        isLoading.postValue(true)
//        parentJob = viewModelScope.launch {
//            val response = repository.addSong(songName, imageFile,songFile,lyrics,albumId,singerId,typeId)
//            if (response is Response.Success) {
//                addSongStatus.postValue(response.data)
//            } else if (response is Response.Failure) {
//                message.postValue(response.errorMessage)
//            }
//
//
//        }
//        registerEventParentJobFinish()
//    }



}