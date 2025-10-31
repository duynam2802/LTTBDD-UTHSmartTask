package com.example.uthsmarttask.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

@Composable
fun HomeScreen(
    navController: NavController,
    // onBackClicked không cần thiết ở HomeScreen vì nó là màn hình chính
    // onBackClicked: () -> Unit
) {
    AppScaffold(
        navController = navController, // Sử dụng navController được truyền vào
        title = "Trang Chủ",
        showBackButton = false,
        showBottomBar = true,
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Áp dụng padding từ Scaffold
                .padding(16.dp), // Thêm padding riêng cho nội dung
            horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa các button
            verticalArrangement = Arrangement.Center // Căn các button ra giữa màn hình
        ) {
            Button(
                onClick = { navController.navigate("product-detail") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("product-detail")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("")  },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("forgot-password")
            }

            // Thêm các button khác nếu cần...
        }
        // --- KẾT THÚC NỘI DUNG ---
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UTHSmartTaskTheme {
        HomeScreen(navController = rememberNavController())
    }
}
