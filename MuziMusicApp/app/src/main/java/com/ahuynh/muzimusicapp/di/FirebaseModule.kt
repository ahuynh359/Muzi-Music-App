package com.ahuynh.muzimusicapp.di

import com.ahuynh.muzimusicapp.utils.Constants.PLAYLIST
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabaseInstance(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorageInstance(): StorageReference {
        return FirebaseStorage.getInstance().getReference("song_image")
    }

    @Named(SONG)
    @Provides
    fun provideSongColRef(db: FirebaseFirestore): CollectionReference {
        return db.collection(SONG)
    }
    @Named(PLAYLIST)
    @Provides
    fun providePlaylistColRef(db: FirebaseFirestore): CollectionReference {
        return db.collection(PLAYLIST)
    }
}