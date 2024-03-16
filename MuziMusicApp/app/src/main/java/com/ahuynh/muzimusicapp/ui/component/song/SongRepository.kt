package com.ahuynh.muzimusicapp.ui.component.song

import com.ahuynh.muzimusicapp.model.LocalSong
import com.ahuynh.muzimusicapp.utils.SongHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songHelper: SongHelper
){

    suspend fun getAllLocalSongs() : List<LocalSong> = withContext(Dispatchers.IO){
        songHelper.getAllLocalSongs()
    }
}