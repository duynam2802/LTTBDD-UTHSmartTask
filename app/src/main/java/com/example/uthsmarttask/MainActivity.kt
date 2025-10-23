package com.example.uthsmarttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.uthsmarttask.screens.SplashScreen
import com.example.uthsmarttask.screens.GetStartedPageTemplate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            onBackPressedDispatcher.addCallback(this) {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                } else {
                    // Cho phép thoát app
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            MyApp(navController)
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {
//    val navController = rememberNavController()

    Surface( modifier = Modifier
        .fillMaxSize()
        .safeDrawingPadding()
//        .background(Color(0xFFFBFDFF))
        ,
        color = Color(0xFFFBFDFF)
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            composable("splash") {
                SplashScreen(navController)
            }

            composable("getstarted1") {
                GetStartedPageTemplate(navController)
            }

        }
    }
}
