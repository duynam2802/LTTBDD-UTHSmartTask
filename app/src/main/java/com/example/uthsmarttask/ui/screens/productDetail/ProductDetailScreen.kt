package com.example.uthsmarttask.ui.screens.productDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    onBackClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    AppScaffold(
        navController = navController,
        title = "Product Detail",
        showBackButton = true,
        showBottomBar = false,
        onBackClicked = onBackClicked

    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.getstartedpage2),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tên sản phẩm
            Text(
                text = "Giày Nike Nam Nữ Chính Hãng - Nike Air Force 1 '07 LV8 - Màu Trắng | JapanSport HF2898-100",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Giá sản phẩm
            Text(
                text = "Giá: 4.000.000₫",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53935)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mô tả sản phẩm
            Text(
                text = "Với giày chạy bộ, từng gram đều quan trọng. Đó là lý do tại sao đế giữa LIGHTSTRIKE PRO mới nhẹ hơn so với phiên bản trước...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
private fun ProductTopAppBar(onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White) // Nền trắng để không bị trong suốt khi cuộn
            .padding(horizontal = 8.dp) // Padding nhỏ hơn cho các icon ở cạnh
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
//                .zIndex(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF03B4FA))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Tiêu đề
        Text(
            text = "Product detail",
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFF0288D1), // Màu xanh đậm hơn cho chữ
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    val navController = rememberNavController()
    UTHSmartTaskTheme {
        ProductDetailsScreen(
            navController = navController,
            onBackClicked = {}
        )
    }

}
