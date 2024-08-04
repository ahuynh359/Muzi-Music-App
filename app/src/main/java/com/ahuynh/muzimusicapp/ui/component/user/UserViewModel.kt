package com.ahuynh.muzimusicapp.ui.component.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.data.service.remote.UserRemoteService
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val appSharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository
)
    : BaseViewModel() {

    var song = MutableLiveData<Song>()
    var isPlaying = MutableLiveData(false)

     fun restoreState(){
        viewModelScope.launch{
            Constants.IS_SHUFFLE = appSharePreferencesHelper.isShuffle()
            Constants.IS_REPEAT = appSharePreferencesHelper.isRepeat()
        }
    }

    fun saveDeviceToken(token : String){
        viewModelScope.launch {
            appSharePreferencesHelper.saveDeviceToken(token)
        }
    }

    fun updateToken(token: String){
        viewModelScope.launch {
            userRepository.updateToken(token)
        }
    }


}