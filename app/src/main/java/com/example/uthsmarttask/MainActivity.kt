package com.example.uthsmarttask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.uthsmarttask.ui.screens.SplashScreen
import com.example.uthsmarttask.ui.screens.GetStartedPageTemplate
import com.example.uthsmarttask.ui.screens.forgotPassword.CreatePasswordScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.ForgotPasswordScreen
import com.example.uthsmarttask.ui.screens.forgotPassword.VerifyCodeScreen
import com.example.uthsmarttask.ui.screens.login.LoginScreen
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme
import com.example.uthsmarttask.ui.screens.login.LoginViewModel
import com.example.uthsmarttask.data.model.User
import com.example.uthsmarttask.ui.screens.home.HomeScreen
import com.example.uthsmarttask.ui.screens.login.ProfileScreen
import com.example.uthsmarttask.ui.screens.productDetail.ProductDetailsScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTHSmartTaskTheme {
                val navController = rememberNavController()

                MyApp(navController)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                val credential = GoogleAuthProvider.getCredential(idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            val user = authResult.result.user
                            Toast.makeText(
                                this,
                                "✅ Đăng nhập thành công: ${user?.displayName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "❌ Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(this, "⚠️ Lỗi đăng nhập Google", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

}

@Composable
fun MyApp(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
//    val navController = rememberNavController()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ -> }

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

            composable("forgot-password") {
                ForgotPasswordScreen(navController, viewModel = viewModel())
            }

            composable("verify-code") {
                VerifyCodeScreen(navController, viewModel = viewModel())
            }

            composable("create-new-password") {
                CreatePasswordScreen(navController = navController, viewModel = viewModel())
            }

            composable("login") {
                LoginScreen(
                    onSignInClick = {
                        viewModel.signInWithGoogle(
                            context = context,
                            launcher = launcher,
                            onSuccess = { user: User ->
                                navController.navigate("profile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onFailure = { message ->
                                println("Lỗi đăng nhập: $message")
                            }
                        )
                    }
                )
            }

            composable("profile") {
                ProfileScreen(navController)
            }

//...


            composable("product-detail") {
                ProductDetailsScreen(
                    navController = navController,
                    onBackClicked = { navController.popBackStack() }
                )
            }

            composable("home") {
                HomeScreen(navController)
            }
        }
    }


}


