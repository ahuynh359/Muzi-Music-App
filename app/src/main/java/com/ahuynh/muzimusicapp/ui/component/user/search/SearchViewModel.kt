package com.ahuynh.muzimusicapp.ui.component.user.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.repository.SearchHistoryRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val searchHistoryRepository: SearchHistoryRepository

) : BaseViewModel() {

    var songs = MutableLiveData<List<Song>>()
    var albums = MutableLiveData<List<Album>>()
    var singers = MutableLiveData<List<Singer>>()
    var searchHistory = MutableLiveData<List<SearchHistoryEntity>>()
    val isSearchDone = MutableLiveData(false)



    fun saveSearchKeywordHistory(keyword: String) {
        viewModelScope.launch {
            searchHistoryRepository.insert(SearchHistoryEntity(keyword, Date()))
        }
    }

    fun getAllSearchHistory() {
        viewModelScope.launch {
            searchHistory.postValue(searchHistoryRepository.getAllSearchHistory())
        }
    }

    fun search(str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {

            val result = songRepository.searchSong(str)
            result?.let {
                songs.postValue(result.songs.toListSong())
                albums.postValue(result.albums.toListAlbum())
                singers.postValue(result.singers.toListSinger())
            }
        }
        isSearchDone.postValue(true)
        registerEventParentJobFinish()
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.deleteAll()
            searchHistory.postValue(searchHistoryRepository.getAllSearchHistory())
        }
    }


}