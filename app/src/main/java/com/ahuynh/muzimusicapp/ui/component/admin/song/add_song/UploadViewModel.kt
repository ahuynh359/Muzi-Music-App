package com.ahuynh.muzimusicapp.ui.component.admin.song.add_song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository,
    private val singerRepository: SingerRepository,
    private val typeRepository: TypeRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var name = ""
    var lyrics  = ""
    var file: File? = null
    var avatar: File? = null
    var albumId: Long ?= null
    var albumName : String = ""
    var singerIds: MutableSet<Long> = mutableSetOf()
    var albumList = MutableLiveData<List<Album>>()
    var singerName : String = ""
    var singerList = MutableLiveData<List<Singer>>()
    var typeName : String = ""
    var typeList = MutableLiveData<List<Type>>()

    var typeIds: MutableSet<Long> = mutableSetOf()
    var addSongStatus = MutableLiveData<Boolean?>()
    var mess: String? = null


    fun addSong() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result =
                songRepository.createSong(name, avatar!!, file!!, lyrics, albumId!!, singerIds, typeIds)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            addSongStatus.postValue(result is Response.Success)


        }
        registerEventParentJobFinish()
    }

    fun getAllAlbums() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            albumList.postValue(albumRepository.getAllAlbums(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }
    fun getAllSingers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            singerList.postValue(singerRepository.getAllSingers(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }

    fun getAllTypes() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllTypes(SortName.A_Z))
        }
        registerEventParentJobFinish()
    }


}