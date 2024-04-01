package com.ahuynh.muzimusicapp.ui.component.song

import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.NAME
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class SongRepository @Inject constructor(
    @Named(SONG)
    private val songCollRef: CollectionReference
) {

    suspend fun getAllSong(order: Constants.SortingOrder): Response<List<Song>> {
        return try {
            val query =
                if (order == Constants.SortingOrder.DESCENDING) Query.Direction.DESCENDING else Query.Direction.ASCENDING
            val songs = songCollRef.orderBy(NAME, query).get().await()
                .toObjects(Song::class.java)
            Response.Success(songs)
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown error")
        }
    }

}