package com.ahuynh.muzimusicapp.ui.component.chart

import androidx.lifecycle.MutableLiveData
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(private val repository: SongRepository) : BaseViewModel() {

    var songOldList = MutableLiveData<List<SongOld>>()





}