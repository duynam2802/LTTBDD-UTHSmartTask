package com.example.uthsmarttask.getStarted

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uthsmarttask.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash"){
        composable("splash") { SplashScreen(navController) }
        composable("getstarted1") { GetStartedPageTemplate(navController) }
    }
}