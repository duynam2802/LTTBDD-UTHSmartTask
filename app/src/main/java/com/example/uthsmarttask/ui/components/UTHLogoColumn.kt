package com.example.uthsmarttask.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthsmarttask.R

@Composable
fun UTHLogoColumn(
    title: String = "SmartTask",
    textColor: Color = Color(0xFF006EE9),
    modifier: Modifier = Modifier
) {
    Column(
//        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(R.drawable.uth_logo)
        Image(
            painter = image,
            contentDescription = "Logo UTH"
        )

        val Righteous = FontFamily(Font(R.font.righteous_regular))

        Text(
            text = title,
            fontSize = 26.sp,
            fontFamily = Righteous,
            fontWeight = FontWeight.W400,
            color = textColor,
            modifier = Modifier.padding(16.dp),
        )
    }
}
