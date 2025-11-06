package com.example.uthsmarttask.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.components.ScreenLevel

@Composable
fun ProfileScreen(navController: NavController) {
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    var isSigningOut by remember { mutableStateOf(false) }

    AppScaffold(
        navController = navController,
        title = "Dữ liệu",
        screenLevel = ScreenLevel.SUB,
        showMoreMenu = false,
        moreMenuItems = {
            },
        showSaveButton = true,
        onBackClicked = { navController.popBackStack() }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
        ) {
            if (firebaseUser != null && !isSigningOut) {

                Spacer(modifier = Modifier.height(150.dp))
                Image(
                    painter = rememberAsyncImagePainter(firebaseUser.photoUrl),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileInfoField(
                    label = "Name",
                    value = firebaseUser.displayName ?: "Không có tên"
                )

                ProfileInfoField(
                    label = "Email",
                    value = firebaseUser.email ?: "Không có email"
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        isSigningOut = true
                        FirebaseAuth.getInstance().signOut()
                        // Quay lại màn hình đăng nhập
                        navController.navigate("sign_in") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Đăng xuất")
                }
            } else if (isSigningOut) {
                CircularProgressIndicator()
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("sign_in") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            }
        }
    }
}


@Composable
fun ProfileInfoField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF555555)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xCECBCBCB), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = value,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = Color.Black
            )
        }
    }
}
