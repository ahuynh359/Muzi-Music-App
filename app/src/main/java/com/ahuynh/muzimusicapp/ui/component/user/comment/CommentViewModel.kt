package com.ahuynh.muzimusicapp.ui.component.user.comment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Comment
import com.ahuynh.muzimusicapp.data.model.request.AddCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.EditCommentRequest
import com.ahuynh.muzimusicapp.data.model.request.ReplyCommentRequest
import com.ahuynh.muzimusicapp.data.model.response.toCommentList
import com.ahuynh.muzimusicapp.data.repository.CommentRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {

    var commentList = MutableLiveData<List<Comment>>()
    var totalComments = MutableLiveData<Int>()
    var addCommentStatus = MutableLiveData<Boolean?>()
    var deleteCommentStatus = MutableLiveData<Boolean?>()
    var updateCommentStatus = MutableLiveData<Boolean?>()
    var replyCommentStatus = MutableLiveData<Boolean?>()
    var mess : String ?= null
    var currentUserId = MutableLiveData<Long>()

    var commentReply = MutableLiveData<Comment?>()


    init {
        currentUserId.postValue(sharePreferencesHelper.getId())
    }


    fun getCommentsOfSong(id: Long) {

        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = commentRepository.getAllCommentsOfSong(id)
            if (result is Response.Success) {
                commentList.postValue(result.data.data.comments.toCommentList())
                totalComments.postValue(result.data.data.totalComments)
            }

        }
        registerEventParentJobFinish()
    }

    fun addCommentToSong(str: String, id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val commentRequest = AddCommentRequest(id, str)
            val result = commentRepository.createComment(commentRequest)
            if (result is Response.Success) {
                mess = result.data.message

            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            addCommentStatus.postValue(result is Response.Success)

        }
        registerEventParentJobFinish()
    }

    fun deleteComment(commentId : Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = commentRepository.deleteComment(commentId)
            if (result is Response.Success) {
                mess = result.data.message

            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            deleteCommentStatus.postValue(result is Response.Success)

        }
        registerEventParentJobFinish()

    }

    fun editComment(editCommentRequest: EditCommentRequest) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = commentRepository.editComment(editCommentRequest)
            if (result is Response.Success) {
                mess = result.data.message

            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            updateCommentStatus.postValue(result is Response.Success)

        }
        registerEventParentJobFinish()
    }

    fun addReply(songId: Long, parentId: Long, str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val replyComment = ReplyCommentRequest(songId, parentId,str)
            val result = commentRepository.replyComment(replyComment)
            if (result is Response.Success) {
                mess = result.data.message

            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            replyCommentStatus.postValue(result is Response.Success)

        }
        registerEventParentJobFinish()
    }


}








