package com.example.uthsmarttask.ui.screens.forgotPassword
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.uthsmarttask.R
//import com.example.uthsmarttask.ui.theme.PoppinsMedium
//import androidx.compose.ui.graphics.Color
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import com.example.uthsmarttask.ui.screens.forgotPassword.sp
//
//@Composable
//fun SuccessResetPasswordScreen(
//    navController: NavController
//) {
//    AuthScreenTemplate(
//        title = stringResource(R.string.password_reset_successful),
//        subtitle = stringResource(R.string.you_can_now_login_with_your_new_password),
//        scrollState = rememberScrollState(),
//        onBackClicked = { navController.popBackStack() }
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            // ✅ Icon hoặc hình minh họa thành công
//            Image(
//                painter = painterResource(id = R.drawable.ic_success), // bạn thêm hình vào res/drawable/ic_success.xml hoặc png
//                contentDescription = "Success",
//                modifier = Modifier
//                    .size(160.dp)
//                    .padding(top = 32.dp)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Text(
//                text = stringResource(R.string.password_reset_successful),
//                // SỬA Ở ĐÂY: Tạo một TextStyle mới
//                style = TextStyle(
//                    fontFamily = PoppinsMedium, // Sử dụng PoppinsMedium làm font family
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF2396F3)
//                ),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = stringResource(R.string.you_can_now_login_with_your_new_password),
//                style = TextStyle(
//                    fontFamily = PoppinsMedium, // Sử dụng PoppinsMedium làm font family
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF2396F3)
//                ),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(48.dp))
//
//            // ✅ Nút "Go to Login"
//            Button(
//                modifier = Modifier
//                    .height(52.dp)
//                    .fillMaxWidth(),
//                onClick = {
//                    // Điều hướng về màn hình đăng nhập
//                    navController.navigate("login") {
//                        popUpTo("createPassword") { inclusive = true }
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2396F3))
//            ) {
//                Text("Go to Login")
//            }
//        }
//    }
//}
//
//private fun Unit.sp(i: Int) {}
