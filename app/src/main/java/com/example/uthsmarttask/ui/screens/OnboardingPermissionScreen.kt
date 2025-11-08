package com.example.uthsmarttask.ui.screens

import android.Manifest
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.uthsmarttask.R // Quan trọng: Đảm bảo bạn có R
import com.google.accompanist.permissions.*

// Lớp niêm phong để quản lý các bước yêu cầu quyền
private sealed class PermissionStep {
    object Location : PermissionStep()
    object Notification : PermissionStep()
    object Camera : PermissionStep()
    object Done : PermissionStep() // Trạng thái khi đã xong
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingPermissionScreen(
    onFinished: () -> Unit // Callback để gọi khi hoàn tất
) {
    // 1. Trạng thái để theo dõi chúng ta đang ở bước nào
    var currentStep by remember { mutableStateOf<PermissionStep>(PermissionStep.Location) }

    // 2. Tạo các permission state cho từng quyền
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Quyền Notification chỉ cần cho API 33 (Android 13) trở lên
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null // Không cần xin quyền này ở API < 33
    }

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    // Hàm tiện ích để chuyển sang bước tiếp theo
    fun moveToNextStep() {
        currentStep = when (currentStep) {
            PermissionStep.Location -> {
                // Nếu không cần xin quyền Noti (API < 33), bỏ qua
                if (notificationPermissionState == null) PermissionStep.Camera else PermissionStep.Notification
            }
            PermissionStep.Notification -> PermissionStep.Camera
            PermissionStep.Camera -> PermissionStep.Done
            PermissionStep.Done -> PermissionStep.Done
        }
    }

    // 3. Hiển thị UI nền (Login, Continue...)
    // Box để cho phép Dialog đè lên trên
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Màu nền mờ
    ) {
        // Đây là UI nền mờ mờ phía sau
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* TODO: Xử lý Login */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { /* TODO: Xử lý Continue */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Continue without account")
            }
        }

        // 4. Hiển thị Dialog dựa trên bước hiện tại
        when (currentStep) {
            PermissionStep.Location -> {
                PermissionRequestDialog(
                    iconResId = R.drawable.ic_success, // BẠN CẦN THÊM ICON NÀY
                    title = "Location",
                    description = "Allow maps to access your location while you use the app?",
                    onAllowClick = {
                        locationPermissionState.launchPermissionRequest()
                        moveToNextStep()
                    },
                    onSkipClick = { moveToNextStep() }
                )
            }
            PermissionStep.Notification -> {
                PermissionRequestDialog(
                    iconResId = R.drawable.back_icon, // BẠN CẦN THÊM ICON NÀY
                    title = "Notification",
                    description = "Please enable notifications to receive updates and reminders",
                    buttonText = "Turn on",
                    onAllowClick = {
                        notificationPermissionState?.launchPermissionRequest()
                        moveToNextStep()
                    },
                    onSkipClick = { moveToNextStep() }
                )
            }
            PermissionStep.Camera -> {
                PermissionRequestDialog(
                    iconResId = R.drawable.back_icon, // BẠN CẦN THÊM ICON NÀY
                    title = "Camera",
                    description = "We need access to your camera to scan QR codes",
                    buttonText = "Turn on",
                    onAllowClick = {
                        cameraPermissionState.launchPermissionRequest()
                        moveToNextStep()
                    },
                    onSkipClick = { moveToNextStep() }
                )
            }
            PermissionStep.Done -> {
                // Khi tất cả dialog đã hiển thị, gọi callback để chuyển màn hình
                // LaunchedEffect để đảm bảo nó chỉ chạy 1 lần
                LaunchedEffect(Unit) {
                    onFinished()
                }
            }
        }
    }
}

/**
 * Composable tùy chỉnh cho Dialog yêu cầu quyền
 */
@Composable
fun PermissionRequestDialog(
    @DrawableRes iconResId: Int,
    title: String,
    description: String,
    buttonText: String = "Allow",
    onAllowClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Dialog(onDismissRequest = onSkipClick) { // Bấm ra ngoài cũng là "skip"
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = title,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Fit
                )

                // Tiêu đề
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Mô tả
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nút "Allow" / "Turn on"
                Button(
                    onClick = onAllowClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50) // Bo tròn
                ) {
                    Text(buttonText, fontSize = 16.sp)
                }

                // Nút "Skip for now"
                OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50) // Bo tròn
                ) {
                    Text("Skip for now", fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPermissionScreenPreview() {
    // Hiển thị preview
    // Trong preview, dialog sẽ không tự động chuyển
    OnboardingPermissionScreen(onFinished = {})
}