package com.ahuynh.muzimusicapp.ui.component.library

import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.model.Song
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LibraryRepository @Inject constructor(
    private val songsCollRef: CollectionReference,
) {

    suspend fun getSongs(): Response<List<Song>> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = songsCollRef.get().await()
            val songs = querySnapshot.toObjects(Song::class.java)
            Response.Success(songs)
        } catch (e: Exception) {
            Response.Failure(e.message.toString())
        }
    }
    fun getAllSongs() = flow {
        try {
            emit(Response.Loading)
            val movies = songsCollRef.get().await().toObjects(Song::class.java)
            emit(Response.Success(movies))
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: "ERROR_MESSAGE"))
        }
    }


}