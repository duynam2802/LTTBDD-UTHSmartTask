package com.example.uthsmarttask.ui.screens.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.components.ScreenLevel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyTaskScreen(navController: NavController) {
    AppScaffold(
        navController = navController,
        title = "Detail",
        showSaveButton = false,
        screenLevel = ScreenLevel.SUB,
        onBackClicked = { navController.navigate("home") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0x54CECECE)),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.assignment_24px),
                    contentDescription = "No Tasks Image",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No Tasks Yet!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stay productive — add something to do",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewEmpty() {
    EmptyTaskScreen(navController = NavHostController(LocalContext.current))
}