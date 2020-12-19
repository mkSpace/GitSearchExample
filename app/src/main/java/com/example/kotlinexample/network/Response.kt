package com.example.kotlinexample.network

data class Response<T>(
    val items: T? = null
)