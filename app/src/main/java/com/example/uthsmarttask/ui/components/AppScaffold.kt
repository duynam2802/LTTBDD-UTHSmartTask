package com.example.uthsmarttask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

@OptIn(ExperimentalMaterial3Api::class) // Thêm annotation này
@Composable
fun AppScaffold(
    navController: NavController,
    title: String,
    showBackButton: Boolean = false,
    showBottomBar: Boolean = true,
    onBackClicked: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = title,
                showBackButton = showBackButton,
                onBackClicked = (onBackClicked ?: { navController.popBackStack() }) as () -> Unit // Thêm hành động mặc định
            )
        },
        bottomBar = {
            if (showBottomBar) {
                // BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp),

        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),

                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClicked,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary)


                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        // Thêm màu sắc cho TopAppBar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    UTHSmartTaskTheme {
        AppScaffold(
            navController = NavController(LocalContext.current),
            title = "Title",
            showBackButton = true,
            showBottomBar = true,
        ) { }
    }

}
