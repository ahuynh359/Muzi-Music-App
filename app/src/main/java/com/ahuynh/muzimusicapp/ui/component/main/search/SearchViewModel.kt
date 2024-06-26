package com.ahuynh.muzimusicapp.ui.component.main.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toListUser
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val songRepository: SongRepository,

) : BaseViewModel() {

    var songs = MutableLiveData<List<Song>>()
    var albums = MutableLiveData<List<Album>>()
    var singers = MutableLiveData<List<Singer>>()
    val isSearchDone = MutableLiveData(false)

    fun search(str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.searchSong(str)
            result?.let {
                songs.postValue(it.songs.toListSong())
                albums.postValue(it.albums.toListAlbum())
                singers.postValue(it.singers.toListSinger())
            }

        }
        isSearchDone.postValue(true)
        registerEventParentJobFinish()
    }


}