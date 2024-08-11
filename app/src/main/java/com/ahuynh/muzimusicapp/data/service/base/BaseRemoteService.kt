package com.ahuynh.muzimusicapp.data.service.base


import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.ResponseError
import retrofit2.Response

open class BaseRemoteService {

    protected suspend fun <T : Any> callApi(call: suspend () -> Response<T>): NetworkResult<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return NetworkResult.Failure(ResponseError("Server Error 500"))
        }

        return if (response.isSuccessful) {
            if (response.body() == null)
                NetworkResult.Failure(
                    ResponseError("Server Without Body")
                )
            else NetworkResult.Success(response.body()!!)
        } else {
            if (response.code() >= 500) {
                NetworkResult.Failure(ResponseError("Server Error " + response.code()))
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                NetworkResult.Failure(ResponseError.fromJson(errorBody))
            }
        }
    }
}