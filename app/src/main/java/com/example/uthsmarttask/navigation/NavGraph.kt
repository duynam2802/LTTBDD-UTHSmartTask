package com.example.uthsmarttask.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uthsmarttask.ui.screens.GetStartedPageTemplate
import com.example.uthsmarttask.ui.screens.SplashScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.ForgotPasswordScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.ForgotPasswordViewModel
import com.example.uthsmarttask.ui.screens.forgotPassword.VerifyCodeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash"){
        composable("splash") { SplashScreen(navController) }
        composable("getstarted1") { GetStartedPageTemplate(navController) }
        composable("forgot-password") {
            val viewModel: ForgotPasswordViewModel = viewModel()
            ForgotPasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable("verify-code") {
            VerifyCodeScreen(navController = navController, viewModel = viewModel())
        }
    }
}