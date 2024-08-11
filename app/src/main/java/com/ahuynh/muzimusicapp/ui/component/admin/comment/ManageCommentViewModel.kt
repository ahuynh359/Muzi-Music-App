package com.ahuynh.muzimusicapp.ui.component.admin.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.repository.CommentRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var deleteCommentStatus = MutableLiveData<Boolean?>()
    var mess: String? = null
    var commentList = MutableLiveData<List<Comment>>()
    var sortComment = MutableLiveData<SortName>()

    init {
        getSortComment()
    }

    fun getSortComment() {
        viewModelScope.launch {
            sortComment.postValue(sharePreferencesHelper.isSortComment())
        }
    }

    fun setSortComment(sortName: SortName) {
        viewModelScope.launch {
            sortComment.postValue(sortName)
            sharePreferencesHelper.setSortSong(sortName)
        }
    }


    fun getAllComments() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            commentList.postValue(commentRepository.getAllComments(sharePreferencesHelper.isSortComment()))

        }
        registerEventParentJobFinish()
    }


    fun deleteComment(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = commentRepository.deleteComment(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            deleteCommentStatus.postValue(result is NetworkResult.Success)
        }

        registerEventParentJobFinish()
    }


}
