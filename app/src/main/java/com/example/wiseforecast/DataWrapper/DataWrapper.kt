package com.example.wiseforecast.DataWrapper

sealed class DataWrapper<T> {
    class Loading<T>: DataWrapper<T>()
    data class Success<T>(var data: T?): DataWrapper<T>()
    data class Error<T>(var message: String?): DataWrapper<T>()
}