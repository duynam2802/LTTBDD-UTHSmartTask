package com.example.uthsmarttask.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.components.ScreenLevel
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

@Composable

fun HomeScreen(navController: NavController) {
        AppScaffold(
            navController = navController,
            title = "Home",
            screenLevel = ScreenLevel.MAIN,

        ) { }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UTHSmartTaskTheme {
        HomeScreen(navController = rememberNavController())
    }
}

