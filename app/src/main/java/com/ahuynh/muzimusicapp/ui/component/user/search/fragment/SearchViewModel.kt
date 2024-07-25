package com.ahuynh.muzimusicapp.ui.component.user.search.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val typeRepository: TypeRepository,
    private val songRepository: SongRepository

) : BaseViewModel() {

    private val isSearchDone = MutableLiveData(false)
    var typeList = MutableLiveData<List<Type>>()

     fun getAllTypes() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllTypes(SortName.NEW))

        }
        isSearchDone.postValue(true)
        registerEventParentJobFinish()
    }





}