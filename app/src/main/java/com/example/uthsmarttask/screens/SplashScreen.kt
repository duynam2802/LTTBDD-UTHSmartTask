package com.example.uthsmarttask.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthsmarttask.R


@Composable

fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate("getstarted1") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(R.drawable.uth_logo)
        Image(
            painter = image,
            contentDescription = "Logo UTH",

            modifier = Modifier
//                .weight(1f)
        )

        val Righteous = FontFamily(
            Font(R.font.righteous_regular)
        )

        Text(
            text = "UTH Smart Task",
            fontSize = 30.sp,
            fontFamily = Righteous,
            fontWeight = FontWeight.W400,
            color = Color(0xFF006EE9),
            modifier = Modifier
                .padding(16.dp),

        )

    }
}


// 3. Tạo một hàm mới chỉ dành cho Preview
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    // 4. Tạo một NavController giả
    val navController = rememberNavController()
    // 5. Gọi Composable gốc với NavController giả
    SplashScreen(navController = navController)
}
