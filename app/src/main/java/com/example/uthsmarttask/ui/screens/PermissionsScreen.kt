package com.example.uthsmarttask.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    onPermissionsResult: () -> Unit
) {
    val permissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)


    val showDialog = remember { mutableStateOf(!multiplePermissionsState.allPermissionsGranted) }


    if (multiplePermissionsState.allPermissionsGranted || !showDialog.value) {
        LaunchedEffect(Unit) {
            onPermissionsResult()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else {
        PermissionDialog(
            onGrantClick = {
                multiplePermissionsState.launchMultiplePermissionRequest()
            },
            onDismiss = {
                showDialog.value = false
            }
        )
    }
}

@Composable
private fun PermissionDialog(
    onGrantClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Yêu cầu quyền truy cập")
        },
        text = {
            Text(
                text = "Để sử dụng đầy đủ các tính năng, ứng dụng cần một vài quyền truy cập. Vui lòng nhấn Cho phép để chấp nhận các quyên!",
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(onClick = onGrantClick) {
                Text("Cấp quyền")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Để sau")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PermissionsScreenPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        PermissionDialog(onGrantClick = {}, onDismiss = {})
    }
}
