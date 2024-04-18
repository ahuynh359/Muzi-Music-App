package com.ahuynh.muzimusicapp.data.repository

import android.util.Log
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class SongRepository @Inject constructor(
    @Named(SONG)
    private val songCollRef: CollectionReference,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getAllSong(): Response<List<Song>> {
        return withContext(dispatcher) {
            try {
                val songs = songCollRef.get().await()
                    .toObjects(Song::class.java)
                Response.Success(songs)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }
    suspend fun updateSongListen(song: Song): Response<Boolean> {
        return withContext(dispatcher) {
            try {

                val currentSong = songCollRef.document(song.id!!)
                val listen = song.listen?.plus(1)
                Log.d("SongRepository",song.listen.toString())
                Log.d("SongRepository",listen.toString())
                val updateData = hashMapOf(
                    "listen" to listen
                )
                currentSong.update(updateData as Map<String, Int?>).await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }


    suspend fun searchSong(name: String): Response<List<Song>> {
        return withContext(dispatcher) {
            try {
                val songs = songCollRef.whereEqualTo("name", name).get().await()
                    .toObjects(Song::class.java)
                Response.Success(songs)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }


}