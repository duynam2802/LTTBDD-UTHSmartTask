package com.example.uthsmarttask.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    // Firebase user trực tiếp từ auth
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    var isSigningOut by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (firebaseUser != null && !isSigningOut) {
            // Ảnh đại diện
            Image(
                painter = rememberAsyncImagePainter(firebaseUser.photoUrl),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Tên và email
            Text(
                text = firebaseUser.displayName ?: "Không có tên",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = firebaseUser.email ?: "Không có email",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nút đăng xuất
            Button(
                onClick = {
                    isSigningOut = true
                    FirebaseAuth.getInstance().signOut()
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Đăng xuất")
            }

        } else {
            LaunchedEffect(Unit) {
                navController.navigate("sign_in") {
                    popUpTo("home") { inclusive = true }
                }
                isSigningOut = false
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable

