package com.ahuynh.muzimusicapp.di

import com.ahuynh.muzimusicapp.utils.Constants.PLAYLIST
import com.ahuynh.muzimusicapp.utils.Constants.SONG
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
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