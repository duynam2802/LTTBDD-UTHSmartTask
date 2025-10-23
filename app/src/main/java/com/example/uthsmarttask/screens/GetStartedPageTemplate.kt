package com.example.uthsmarttask.screens

import android.R.attr.maxWidth
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import com.example.uthsmarttask.model.GetStartedItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily




val pages = listOf(
    GetStartedItem(
        "Easy Time Management",
        "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first ",
        R.drawable.getstartedpage1
    ),
    GetStartedItem(
        "Increase Work Effectiveness",
        "Time management and the determination of more important tasks will give your job statistics better and always improve",
        R.drawable.getstartedpage2
    ),
    GetStartedItem(
        "Reminder Notification",
        "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set",
        R.drawable.getstartedpage3
    )
)
@Composable
fun GetStartedPageTemplate(navController: NavController) {
    var currentPage by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFBFDFF))
    ) {
        // Hàng trên cùng: 3 chấm + nút skip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 3 chấm chỉ báo
            Row(

            ) {
                repeat(pages.size) { index ->
                    val color = if (index == currentPage) Color(0xFF006EE9) else Color(0xFFEEF5FD)
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .padding(2.dp)
                            .background(color, CircleShape)
                    )
                }
            }

            // Nút Skip
            TextButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .defaultMinSize(minWidth = 0.dp, minHeight = 0.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF006EE9)
                )
            ) {
                Text("skip")
            }


        }


//        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ảnh minh họa
            Image(
                painter = painterResource(id = pages[currentPage].image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tiêu đề
            Text(
                text = pages[currentPage].title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
//                fontWeight = FontWeight.W500,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mô tả
            Text(
                text = pages[currentPage].description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                modifier = Modifier.fillMaxWidth()
            )

//            Spacer(modifier = Modifier.weight(1f))
        }


        // --- Bottom Buttons ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back
            if (currentPage > 0) {
                Button(
                    onClick = { currentPage-- },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),   // màu trong suốt
                    contentPadding = PaddingValues(0.dp),   // bỏ padding
                    modifier = Modifier.size(53.dp)   // size 53px
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = "back"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

            }

            // Next / Get Started
            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        navController.navigate("home")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0092F2),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(52.23.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (currentPage == pages.size - 1) "Get Started" else "Next",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GetStartedPageTemplatePreview() {

    val navController = rememberNavController()


    GetStartedPageTemplate(navController = navController)
}
