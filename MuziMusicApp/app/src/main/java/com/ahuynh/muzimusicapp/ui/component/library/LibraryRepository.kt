package com.ahuynh.muzimusicapp.ui.component.library

import com.ahuynh.muzimusicapp.model.Playlist
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants.PLAYLIST
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Constants.SONG_ID
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LibraryRepository @Inject constructor(
    private val database: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val songsCollRef: CollectionReference,
) {

    fun getPlaylist(result: (Response<List<Playlist>>) -> Unit) {
        database.collection(PLAYLIST)
            .orderBy(SONG_ID, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val playlists = it.toObjects(Playlist::class.java)
                result.invoke(
                    Response.Success(playlists)
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

    fun getSongs(result: (Response<List<Song>>) -> Unit) {
        database.collection(SONG)
            //.whereEqualTo(SONG_ID, song?.id)
            .orderBy(SONG_ID, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
//                val songs = arrayListOf<Song>()
//                for (document in it) {
//                    val note = document.toObject(Song::class.java)
//                    songs.add(note)
//                }
                val songs = it.toObjects(Song::class.java)
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


}