package com.ahuynh.muzimusicapp.ui.component.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.component.library.LibraryRepository
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel  @Inject constructor(private val libraryRepository: LibraryRepository) : ViewModel(){
    var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)
    var song: MutableLiveData<Song> = MutableLiveData()
    var songList: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    var isClear: Boolean = false
}