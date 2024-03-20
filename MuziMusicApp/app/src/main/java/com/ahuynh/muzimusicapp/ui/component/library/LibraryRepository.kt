package com.ahuynh.muzimusicapp.ui.component.library

import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_ID
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LibraryRepository @Inject constructor(
    private val database: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val songsCollRef: CollectionReference,
) {

    fun getSongs( result: (Response<List<Song>>) -> Unit) {
        database.collection(SONG)
            //.whereEqualTo(SONG_ID, song?.id)
            .orderBy(SONG_ID, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val songs = arrayListOf<Song>()
                for (document in it) {
                    val note = document.toObject(Song::class.java)
                    songs.add(note)
                }
                result.invoke(
                    Response.Success(songs)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Response.Failure(
                        it.localizedMessage ?: "Error"
                    )
                )
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