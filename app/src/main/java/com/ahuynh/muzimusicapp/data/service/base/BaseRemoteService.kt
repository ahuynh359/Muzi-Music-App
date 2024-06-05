package com.ahuynh.muzimusicapp.data.service.base

import retrofit2.Response

open class BaseRemoteService {

    protected suspend fun <T : Any> callApi(call: suspend () -> Response<T>): com.ahuynh.muzimusicapp.utils.Response<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return com.ahuynh.muzimusicapp.utils.Response.Failure("Server Error 500")
        }

        return if (response.isSuccessful) {
            if (response.body() == null)
                com.ahuynh.muzimusicapp.utils.Response.Failure(
                    "Response without body 200"
                )
            else com.ahuynh.muzimusicapp.utils.Response.Success(response.body()!!)
        } else {
            if (response.code() >= 500) {
                com.ahuynh.muzimusicapp.utils.Response.Failure("Server Error " + response.code())
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                com.ahuynh.muzimusicapp.utils.Response.Failure(errorBody)
            }
        }
    }
}