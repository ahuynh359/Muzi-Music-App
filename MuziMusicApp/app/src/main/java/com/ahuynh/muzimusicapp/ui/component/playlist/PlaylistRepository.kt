package com.ahuynh.muzimusicapp.ui.component.playlist

import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlaylistRepository @Inject constructor(
    @Named(Constants.PLAYLIST)
    private val playlistCollRef: CollectionReference
) {
    suspend fun getAllPlaylist(order: Constants.SortingOrder): Response<List<Playlist>> {
        return try {
            val query =
                if (order == Constants.SortingOrder.DESCENDING) Query.Direction.DESCENDING else Query.Direction.ASCENDING
            val songs = playlistCollRef.orderBy(Constants.NAME, query).get().await()
                .toObjects(Playlist::class.java)
            Response.Success(songs)
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown error")
        }
    }

    suspend fun addPlaylist(playlist: PlaylistModel): Response<Boolean> {
        return try {
            val play = hashMapOf(
                "image" to "https://firebasestorage.googleapis.com/v0/b/muzimusic-c2598.appspot.com/o/app%2Fplaylist.png?alt=media&token=aa0448ad-b60d-4f76-b7b5-a83162dd7a10",
                "name" to playlist.namePlaylist,
                "songs" to listOf<String>()
            )

            playlistCollRef.add(play).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e.message ?: "Unknown error")
        }
    }
}