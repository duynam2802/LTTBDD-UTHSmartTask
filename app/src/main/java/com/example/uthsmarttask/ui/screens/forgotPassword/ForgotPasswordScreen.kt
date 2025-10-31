package com.example.uthsmarttask.ui.screens.forgotPassword

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.uthsmarttask.ui.screens.AuthScreenTemplate
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    val emailPattern =
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return Pattern.matches(emailPattern, email)
}

@Composable
fun ForgotPasswordScreen(navController: NavController, viewModel: ForgotPasswordViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var emailError by remember { mutableStateOf(false) }

    AuthScreenTemplate(
        title = stringResource(R.string.forget_password),
        subtitle = stringResource(R.string.enter_your_email_we_will_send_you_a_verification_code),
        scrollState = scrollState,
        onBackClicked = {
            println("✅ onBackClicked chạy rồi nè")
            Toast.makeText(
                context,
                "PopBackStack called",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.email.value,
            onValueChange = {
                viewModel.email.value = it
                emailError = !isValidEmail(it) && it.isNotEmpty()
            },
            label = { Text(stringResource(R.string.your_email), color = Color.Gray) },
            isError = emailError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Mail",
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF006EE9),
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color(0xFF006EE9),
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        if (emailError) {
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Email không hợp lệ",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.sendResetCode {
                    navController.navigate("verify-code") {

                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2396F3)),
        ) {
            Text("Next")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {

    val navController = rememberNavController()


    ForgotPasswordScreen(navController = navController, viewModel = viewModel())
}
