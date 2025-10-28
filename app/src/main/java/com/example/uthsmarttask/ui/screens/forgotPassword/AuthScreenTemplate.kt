package com.example.uthsmarttask.ui.screens.forgotPassword

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.components.UTHLogoColumn

@Composable
fun AuthScreenTemplate(
    title: String,
    subtitle: String,
    scrollState: ScrollState,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp)
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
