package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Constants.NAME
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
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

    suspend fun getAllSong(order: Constants.SortingOrder): Response<List<Song>> {
        return withContext(dispatcher) {
            try {
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

}