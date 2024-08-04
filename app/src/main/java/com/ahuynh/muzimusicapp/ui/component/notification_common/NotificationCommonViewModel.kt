package com.ahuynh.muzimusicapp.ui.component.notification_common


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationCommonViewModel @Inject constructor(
    private val songRepository: SongRepository,
) : BaseViewModel() {
     suspend fun getSong(idSong: Long) : Song? {
        return songRepository.getSongById(idSong);
    }




}