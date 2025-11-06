package com.example.uthsmarttask.ui.screens.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttask.data.model.TaskItem
import com.example.uthsmarttask.data.network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TaskDetailUiState(
    val isLoading: Boolean = false,
    val task: TaskItem? = null,
    val error: String? = null,
    val isSuccess: Boolean = true
)



class TaskDetailViewModel : ViewModel() {
    val _uiState = MutableStateFlow(TaskDetailUiState(isLoading = true))
    val uiState: StateFlow<TaskDetailUiState> = _uiState

    val _isSuccess = MutableStateFlow(TaskDetailUiSuccess(isLoading = true))

    val isSuccess: StateFlow<TaskDetailUiState> = _uiState

    fun loadTaskById(taskId: Int) {
        viewModelScope.launch {
            _uiState.value = TaskDetailUiState(isLoading = true)
            try {
                val response = RetrofitInstance.api.getTaskById(taskId)

                if (response.isSuccess) { // nếu ApiResponse có trường success
                    _uiState.value = TaskDetailUiState(
                        isLoading = false,
                        task = response.data
                    )
                } else {
                    _uiState.value = TaskDetailUiState(
                        isLoading = false,
                        error = response.message ?: "Unknown error"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }


    fun deleteTask() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true) // Bắt đầu loading
            try {
                // Lấy taskId hiện tại từ uiState
                val taskIdToDelete = uiState.value.task?.id ?: throw IllegalStateException("Task ID not found")

                delay(1000) // Giả lập độ trễ mạng
                // -------------------

                _uiState.value = _uiState.value.copy(isLoading = false, isTaskDeleted = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Failed to delete task: ${e.message}")
            }
        }
    }

    data class TaskDetailUiState(
        val isLoading: Boolean = false,
        val task: TaskItem? = null,
        val error: String? = null,
        val isTaskDeleted: Boolean = false // Thêm dòng này
    )

    data class TaskDetailUiSuccess(
        val isLoading: Boolean = false,
        val task: TaskItem? = null,
        val error: String? = null,
        val isTaskDeleted: Boolean = false, // Thêm dòng này
        val isSuccess: Boolean = true
    )

}
