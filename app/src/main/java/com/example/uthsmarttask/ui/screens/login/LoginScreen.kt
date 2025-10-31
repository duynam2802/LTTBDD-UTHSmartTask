package com.example.uthsmarttask.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme


@Composable
fun LoginScreen(onSignInClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource (R.drawable.uth_logo),
                contentDescription = "Forget Password Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape),
            )
            Text(
                "Smart Tasks",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(150.dp))

            Text(
                text = stringResource(R.string.welcome),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )

            Text(
                stringResource(R.string.ready_to_explore_log_in_to_get_started),
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                textAlign = TextAlign.Center,
                color = Color(0xFF535250)
            )

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),

                colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4EEFF), // Màu nền
                        contentColor = Color(0xFF080548) // Màu chữ
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "SIGN IN WITH GOOGLE",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }
        Text(
            text = "© UTHSmartTasks",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UTHSmartTaskTheme {
        LoginScreen(onSignInClick = {})
    }

}