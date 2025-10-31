package com.example.uthsmarttask.ui.screens

import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.components.UTHLogoColumn


@Composable
fun AuthScreenTemplate(
    title: String,
    subtitle: String,
    scrollState: ScrollState,
    onBackClicked: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        // back button
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
//                    color = MaterialTheme.colorScheme.primary,
                    color = Color(0xFF03B4FA)
                )

        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White
            )


        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UTHLogoColumn()

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = subtitle,
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(16.dp))

            content() // phần nội dung riêng của từng màn hình
        }
    }
}

@Preview( showBackground = true)
@Composable
fun AuthScreenTemplatePreview() {
    AuthScreenTemplate(
        title = "Preview Title",
        subtitle = "This is a longer subtitle to see how it looks when it wraps to multiple lines.",
        scrollState = rememberScrollState(),
        onBackClicked = {}, // Cung cấp một lambda trống cho Preview
        content = {
            // Đây là nơi bạn có thể đặt nội dung giả lập
            // Ví dụ, một Button
            Button(onClick = { /*TODO*/ }) {
                Text("Sample Button")
            }
        }
    )
}
