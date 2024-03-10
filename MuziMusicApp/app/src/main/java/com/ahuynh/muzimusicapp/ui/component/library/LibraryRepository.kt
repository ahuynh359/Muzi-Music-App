package com.ahuynh.muzimusicapp.ui.component.library

import com.ahuynh.muzimusicapp.model.Response
import com.ahuynh.muzimusicapp.model.Song
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LibraryRepository @Inject constructor(
    private val moviesCollRef: CollectionReference,
) {
    fun getAllSongs() = flow {
        try {
            emit(Response.Loading)
            val movies = moviesCollRef.get().await().toObjects(Song::class.java)
            emit(Response.Success(movies))
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: "ERROR_MESSAGE"))
        }
    }


}