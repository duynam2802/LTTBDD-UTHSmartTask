package com.example.uthsmarttask.data.model


data class ApiResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val data: T
)
