package com.example.uthsmarttask.data.network

import com.example.uthsmarttask.data.model.ApiResponse
import com.example.uthsmarttask.data.model.TaskItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/researchUTH/tasks")
    suspend fun getTasks(): ApiResponse<List<TaskItem>>

    @GET("api/researchUTH/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): ApiResponse<TaskItem>
}