package com.ahuynh.muzimusicapp.ui.component.user.type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TypeViewModel @Inject constructor(
    private val typeRepository: TypeRepository,
) : BaseViewModel() {

    var songOfType = MutableLiveData<List<Song>>()

    fun getSongOfType(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songOfType.postValue(typeRepository.getSongFromType(id))
        }
        registerEventParentJobFinish()
    }


}












