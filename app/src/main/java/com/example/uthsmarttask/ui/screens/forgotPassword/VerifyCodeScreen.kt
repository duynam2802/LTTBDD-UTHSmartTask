package com.example.uthsmarttask.ui.screens.forgotPassword

import ads_mobile_sdk.h6
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.components.UTHLogoColumn
import com.example.uthsmarttask.ui.screens.GetStartedPageTemplate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.uthsmarttask.ui.theme.PoppinsMedium
import java.util.regex.Pattern




@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6, // Trong hình của bạn là 6 ô
    onOtpTextChange: (String) -> Unit
) {
    // BasicTextField là nền tảng để xử lý việc nhập liệu
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            // Chỉ cho phép nhập số và giới hạn độ dài bằng otpCount
            if (it.length <= otpCount && it.all { char -> char.isDigit() }) {
                onOtpTextChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword // Hiển thị bàn phím số
        ),
        decorationBox = {
            // Đây là nơi chúng ta "vẽ" giao diện 6 ô vuông
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lặp qua số lượng ô (6 lần)
                repeat(otpCount) { index ->
                    // Lấy ký tự tại vị trí index, nếu không có thì là rỗng
                    val char = otpText.getOrNull(index)?.toString() ?: ""

                    // Xác định xem ô này có đang được "focus" không
                    // (Tức là vị trí con trỏ đang ở đây)
                    val isFocused = otpText.length == index

                    // Vẽ từng ô
                    Box(
                        modifier = Modifier
                            .size(50.dp) // Kích thước của mỗi ô
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF0F0F0))
                            .border(
                                width = 1.dp,
                                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        // Hiển thị ký tự bên trong ô
                        Text(
                            text = char,
                            style = MaterialTheme.typography.headlineSmall, // Kiểu chữ
                            fontFamily = PoppinsMedium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun VerifyCodeScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    // 1. Lấy trạng thái UI từ ViewModel
    val uiState by viewModel.uiState

    // 2. Sử dụng LaunchedEffect để xử lý khi verify thành công
    // Nó sẽ chỉ chạy khi `uiState.success` thay đổi thành true
    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            // Khi thành công, chuyển sang màn hình tạo mật khẩu mới
            // và reset lại trạng thái để không bị lặp lại
            navController.navigate("create-new-password") // <-- Tên route cho màn hình tiếp theo
//            viewModel.resetState() // <-- Rất quan trọng: Reset lại state
        }
    }

    AuthScreenTemplate(
        title = stringResource(R.string.verify_code),
        subtitle = stringResource(R.string.enter_the_the_code_we_just_sent_you_on_your_registered_email),
        scrollState = rememberScrollState()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 3. Sử dụng `viewModel.code.value` để lưu trữ và hiển thị OTP
            OtpTextField(
                modifier = Modifier.fillMaxWidth(),
                otpText = viewModel.code.value,
                onOtpTextChange = { viewModel.code.value = it } // Cập nhật trực tiếp vào ViewModel
            )

            // 4. Hiển thị thông báo lỗi nếu có
            if (uiState.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                // 5. Nút bấm bị vô hiệu hóa khi đang loading
                enabled = !uiState.isLoading,
                onClick = {
                    // 6. Gọi hàm verifyCode từ ViewModel
                    viewModel.verifyCode(
                        onSuccess = {
                            // Xử lý thành công đã được chuyển vào LaunchedEffect ở trên
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2396F3)),
            ) {
                // 7. Hiển thị vòng xoay loading hoặc chữ "Next"
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Next")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VerifyCodeScreenPreview() {

    val navController = rememberNavController()


    VerifyCodeScreen(navController = navController, viewModel = viewModel())
}
