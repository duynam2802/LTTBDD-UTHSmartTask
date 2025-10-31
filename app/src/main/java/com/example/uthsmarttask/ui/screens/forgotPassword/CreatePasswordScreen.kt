package com.example.uthsmarttask.ui.screens.forgotPassword

import android.R.attr.tint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.uthsmarttask.ui.theme.PoppinsMedium




@Composable
fun CreatePasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    AuthScreenTemplate(
        title = stringResource(R.string.create_new_password),
        subtitle = stringResource(R.string.your_new_password_must_be_different_form_previously_used_password),
        scrollState = rememberScrollState(),
        onBackClicked = { navController.popBackStack() }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = viewModel.newPassword.value,
                onValueChange = {
                    viewModel.newPassword.value = it
                    viewModel.passwordsMatch.value = (it == viewModel.confirmPassword.value)
                },
                label = { Text(stringResource(R.string.new_password),  color = Color.Gray) },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {

                        Icon(image, contentDescription = null, tint = Color.Gray)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.confirmPassword.value,
                onValueChange = {
                    viewModel.confirmPassword.value = it
                    viewModel.passwordsMatch.value = (it == viewModel.newPassword.value)
                },
                label = { Text("Confirm Password",  color = Color.Gray) },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {

                        Icon(image, contentDescription = null, tint = Color.Gray )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = !viewModel.passwordsMatch.value,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            )

            if (!viewModel.passwordsMatch.value) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ---------------- BUTTON ----------------
            val passwordsMatch = viewModel.passwordsMatch.value

            Button(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (passwordsMatch && viewModel.newPassword.value.isNotEmpty()) {
                        viewModel.resetPassword {
//                            Toast.makeText(context, "Password reset successfully", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val context = null
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2396F3)),
                enabled = passwordsMatch && viewModel.newPassword.value.isNotEmpty()
            ) {
                Text("Next")
            }

        }
    }
}




@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {

    val navController = rememberNavController()


    CreatePasswordScreen(navController = navController, viewModel = viewModel())
}
