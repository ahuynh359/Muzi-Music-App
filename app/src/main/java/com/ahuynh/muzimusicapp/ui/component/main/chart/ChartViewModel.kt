package com.ahuynh.muzimusicapp.ui.component.main.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(

    private val songRepository: SongRepository,

) : BaseViewModel() {

    var chartList = MutableLiveData<List<Song>>()


    init {
        getChartList()




    }




    fun getChartList() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            chartList.postValue(songRepository.getTop10())
        }
        registerEventParentJobFinish()
    }





}












