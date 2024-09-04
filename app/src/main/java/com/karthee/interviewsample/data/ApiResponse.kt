package com.karthee.interviewsample.data

sealed class ApiResponse<out T> {
    data class ERROR(val msg: String, val code: Int) : ApiResponse<Nothing>()
    data object LOADING : ApiResponse<Nothing>()
    data class SUCCESS<T>(val t: T?) : ApiResponse<T>()
}