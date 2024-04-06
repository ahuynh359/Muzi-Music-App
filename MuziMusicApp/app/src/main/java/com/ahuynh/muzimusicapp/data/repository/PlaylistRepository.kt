package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.data.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlaylistRepository @Inject constructor(
    @Named(Constants.PLAYLIST)
    private val playlistCollRef: CollectionReference,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getAllPlaylist(order: Constants.SortingOrder): Response<List<Playlist>> {
        return withContext(dispatcher) {
            try {
                val query =
                    if (order == Constants.SortingOrder.DESCENDING) Query.Direction.DESCENDING else Query.Direction.ASCENDING
                val songs = playlistCollRef.orderBy(Constants.NAME, query).get().await()
                    .toObjects(Playlist::class.java)
                Response.Success(songs)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun addPlaylist(playlist: PlaylistModel): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val existingPlaylistQuery = playlistCollRef
                    .whereEqualTo("name", playlist.namePlaylist)
                    .get()
                    .await()

                if (!existingPlaylistQuery.isEmpty) {
                    return@withContext Response.Failure("Add Playlist with name ${playlist.namePlaylist} already exists")
                }
                val id = UUID.randomUUID().toString()
                val play = hashMapOf(
                    "id" to id,
                    "image" to "https://firebasestorage.googleapis.com/v0/b/muzimusic-c2598.appspot.com/o/app%2Fplaylist.png?alt=media&token=aa0448ad-b60d-4f76-b7b5-a83162dd7a10",
                    "name" to playlist.namePlaylist,
                    "songs" to listOf<String>()
                )

                playlistCollRef.document(id).set(play).await()
                Response.Success(true)

            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun deletePlaylist(playlist: Playlist): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val playlistRef = playlistCollRef.document(playlist.id!!)
                playlistRef.delete().await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun updatePlaylist(playlist: Playlist, newName: String): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val existingPlaylistQuery = playlistCollRef
                    .whereEqualTo("name", newName)
                    .get()
                    .await()

                if (!existingPlaylistQuery.isEmpty) {
                    return@withContext Response.Failure("Update Playlist with name $newName already exists")
                }
                val playlistRef = playlistCollRef.document(playlist.id!!)
                val updateData = hashMapOf(
                    "name" to newName
                )
                playlistRef.update(updateData as Map<String, Any>).await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }
}