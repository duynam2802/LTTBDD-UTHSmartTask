package com.example.uthsmarttask.data.repository

import com.example.uthsmarttask.data.model.TaskItem

import com.example.uthsmarttask.data.network.RetrofitInstance

class TaskRepository {
    suspend fun getTasks(): Result<List<TaskItem>> {
        return try {
            val response = RetrofitInstance.api.getTasks()
            if (response.isSuccess) Result.success(response.data)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
