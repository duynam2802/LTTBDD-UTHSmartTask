package com.example.uthsmarttask.ui.screens.home

// trong file ui.screens.home.HomeViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttask.data.model.TaskItem // Import model mới
import com.example.uthsmarttask.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// HomeUiState giữ nguyên, vì nó vẫn chỉ cần lưu List<TaskItem>
data class HomeUiState(
    val isLoading: Boolean = false,
    val tasks: List<TaskItem> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Gọi API, nhận về TaskResponse
                val response = RetrofitInstance.api.getTasks()

                // Kiểm tra API có thành công không
                if (response.isSuccess) {
                    // Thành công: Cập nhật state với `response.data`
                    _uiState.update {
                        it.copy(isLoading = false, tasks = response.data)
                    }
                } else {
                    // API trả về lỗi: Cập nhật state với `response.message`
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }
            } catch (e: Exception) {
                // Lỗi mạng/Hệ thống: Cập nhật state với lỗi exception
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown network error")
                }
            }
        }
    }
}