package com.ahuynh.muzimusicapp.utils

import com.beust.klaxon.Klaxon

sealed class NetworkResult<out T> {

    data class Success<out T>(
        val data: T
    ) : NetworkResult<T>()

    data class Failure(
        val errorMessage: ResponseError
    ) : NetworkResult<Nothing>()
}

data class ResponseError(val message: String? = null) {
    companion object {
        fun fromJson(error: String): ResponseError {
            return try {
                val response = Klaxon().parse<ResponseMessage>(error)
                ResponseError(response?.message)
            } catch (e: Exception) {
                ResponseError("Server Error")
            }
        }
    }
}


data class ResponseMessage(val message: String)