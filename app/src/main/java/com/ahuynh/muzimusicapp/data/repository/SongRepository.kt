package com.ahuynh.muzimusicapp.data.repository

import android.net.Uri
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.SongPost
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class SongRepository @Inject constructor(
    @Named(SONG)
    private val songCollRef: CollectionReference,
    private val storage: FirebaseStorage,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun getSongs() = callbackFlow {
        val snapshotListener = songCollRef.orderBy("name").addSnapshotListener { snapshot, e ->
            val songsResponse = if (snapshot != null) {
                val songs = snapshot.toObjects(Song::class.java)
                Response.Success(songs)
            } else {
                Response.Failure(e?.message ?: "Unknown error")
            }
            trySend(songsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }



    suspend fun addSong(newSong: SongPost): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val id = UUID.randomUUID().toString()
                val song = Song(
                    id = id,
                    name = newSong.name,
                    file = newSong.file,
                    image = newSong.image,
                    lyrics = newSong.lyrics,
                    singer = newSong.singer,
                    listen = 0,
                    love = false,
                    listens = mutableMapOf()

                )
                songCollRef.document(id).set(song).await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }



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

    suspend fun deleteSong(songId: String) : Response<Boolean> {
        return withContext(dispatcher){
            try {
                songCollRef.document(songId).delete().await()
                Response.Success(true)
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

    suspend fun updateSongWithCurrentDate(song: Song, currentDate: String): Response<Boolean> {
        return withContext(dispatcher) {
            try {


                val listenCountForCurrentDate =
                    (song.listens[currentDate] ?: 0) + 1 // Increment listen count for current date
                val data = hashMapOf(
                    "listens.$currentDate" to listenCountForCurrentDate
                )
                songCollRef.document(song.id!!).update(data as Map<String, Any>).await()



                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }


    suspend fun searchSongs(name: String): Response<List<Song>> {
        return withContext(dispatcher) {
            try {
                val productsQuery = songCollRef.startAt(name).endAt("$name\uf8ff").get().await()
                val songs = productsQuery.toObjects(Song::class.java)
                Response.Success(songs)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun getAllSongByListen(): Response<List<Song>> {
        return withContext(dispatcher) {
            try {
                val songs = songCollRef.orderBy("listen",Query.Direction.DESCENDING).get().await()
                    .toObjects(Song::class.java)
                Response.Success(songs)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun updateSongLoveStatus(id: String, newLoveStatus: Boolean) : Response<Boolean>{
        return withContext(dispatcher) {
            try {
                val songDocRef = songCollRef.document(id)
                songDocRef.update("love", newLoveStatus).await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun addImageToFirebaseStorage(image: File): Response<String> {
        return withContext(dispatcher) {
            try {
                val imageUri = Uri.fromFile(image)
                val storage = storage.reference.child("/app").child(image.name)
                storage.putFile(imageUri).await().storage.downloadUrl.await()
                val downloadUrl = storage.downloadUrl.await().toString()
                Response.Success(downloadUrl)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun addFileToFirebaseStorage(file: File): Response<String> {
        return withContext(dispatcher) {
            try {
                val imageUri = Uri.fromFile(file)
                val storage = storage.reference.child("/app").child(file.name)
                storage.putFile(imageUri).await().storage.downloadUrl.await()
                val downloadUrl = storage.downloadUrl.await().toString()
                Response.Success(downloadUrl)
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }


}