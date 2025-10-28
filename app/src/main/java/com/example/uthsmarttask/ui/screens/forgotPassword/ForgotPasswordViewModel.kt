package com.example.uthsmarttask.ui.screens.forgotPassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ForgotPasswordUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class ForgotPasswordViewModel : ViewModel() {
    var email = mutableStateOf("")
    var code = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var uiState = mutableStateOf(ForgotPasswordUiState())

    fun sendResetCode(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState.value = ForgotPasswordUiState(isLoading = true)
            delay(1500)
            if (email.value.contains("@")) {
                uiState.value = ForgotPasswordUiState(success = true)
                onSuccess()
            } else {
                uiState.value = ForgotPasswordUiState(error = "Invalid email")
            }
        }
    }

    fun verifyCode(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState.value = ForgotPasswordUiState(isLoading = true)
            delay(1000)
            if (code.value == "111111") {
                uiState.value = ForgotPasswordUiState(success = true)
                onSuccess()
            } else {
                uiState.value = ForgotPasswordUiState(error = "Invalid code")
            }
        }
    }

    fun resetPassword(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState.value = ForgotPasswordUiState(isLoading = true)
            delay(1000)
            if (newPassword.value == confirmPassword.value && newPassword.value.length >= 6) {
                uiState.value = ForgotPasswordUiState(success = true)
                onSuccess()
            } else {
                uiState.value = ForgotPasswordUiState(error = "Passwords do not match or too short")
            }
        }
    }
}
