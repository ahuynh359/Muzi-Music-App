package com.ahuynh.muzimusicapp.ui.component.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {
    fun getAllSongs() = liveData(Dispatchers.IO) {
        repository.getAllSongs().collect { response ->
            emit(response)
        }
    }
}