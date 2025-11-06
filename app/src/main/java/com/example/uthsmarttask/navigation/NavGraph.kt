package com.example.uthsmarttask.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uthsmarttask.ui.screens.GetStartedPageTemplate
import com.example.uthsmarttask.ui.screens.home.HomeScreen
import com.example.uthsmarttask.ui.screens.TestScreen
import com.example.uthsmarttask.ui.screens.SplashScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.CreatePasswordScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.ForgotPasswordScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.ForgotPasswordViewModel
import com.example.uthsmarttask.ui.screens.forgotPassword.VerifyCodeScreen
import com.example.uthsmarttask.ui.screens.login.LoginScreen
import com.example.uthsmarttask.ui.screens.login.ProfileScreen
import com.example.uthsmarttask.ui.screens.productDetail.ProductDetailsScreen

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
        composable ("create-new-password") {
            CreatePasswordScreen(navController = navController, viewModel = viewModel())
        }


        composable("login") {
            LoginScreen (viewModel())
        }

        composable("product-detail") {
            ProductDetailsScreen(
                navController = navController,
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("test") {
            TestScreen(navController)
        }

        composable("home") { HomeScreen((navController))}



    }
}