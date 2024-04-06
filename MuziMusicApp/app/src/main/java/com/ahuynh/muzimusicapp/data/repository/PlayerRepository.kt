package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.utils.Constants
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    @Named(Constants.PLAYLIST)
    private val playlistCollRef: CollectionReference
) {
}