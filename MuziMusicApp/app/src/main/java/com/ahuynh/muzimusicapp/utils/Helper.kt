package com.ahuynh.muzimusicapp.utils

import android.content.Context
import android.content.Intent
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.service.MusicService

object Helper {

    fun sendMusic(
        context: Context,
        action: Int,
        song: Song? = null,
        songList: ArrayList<Song> = arrayListOf()
    ) {
        val intent = Intent(context , MusicService::class.java)
        intent.putExtra("action",action)
        song?.let{

        }

    }
}