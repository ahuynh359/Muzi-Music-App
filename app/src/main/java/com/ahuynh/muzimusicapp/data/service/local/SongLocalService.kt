package com.ahuynh.muzimusicapp.data.service.local

import com.ahuynh.muzimusicapp.data.database.dao.AlbumDao
import com.ahuynh.muzimusicapp.data.database.dao.SingerDao
import com.ahuynh.muzimusicapp.data.database.dao.SongDao
import com.ahuynh.muzimusicapp.data.database.dao.TypeDao
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import javax.inject.Inject

class SongLocalService @Inject constructor(
    private val songDao: SongDao,

) {
    suspend fun insertSong(song : SongEntity){

        songDao.insertSong(song)

    }
    suspend fun getRecentSongs() : List<SongEntity>{
        return songDao.getRecentSongs()
    }

    suspend fun clearRecentSongs() {
        songDao.deleteAll()

    }


}