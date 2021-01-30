package io.budge.goobois.utils

import retrofit2.Response

const val GENERIC_ERROR_MESSAGE = "An error occurred, Please try again"
const val GENERIC_ERROR_CODE = "-1"

fun <T : Any> getAPIResult(response: Response<T>): Result<T> {
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            return Result.Success(body)
        }
    }
    else {
        val errorBody = response.errorBody()
        if (errorBody != null) {
            return Result.Error(
                GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE
            )
        }
    }
    // Fallback to regular status code and message
    return Result.Error("${response.code()}", response.message())
}

