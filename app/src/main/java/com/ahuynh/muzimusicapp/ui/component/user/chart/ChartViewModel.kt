package com.ahuynh.muzimusicapp.ui.component.user.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
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












