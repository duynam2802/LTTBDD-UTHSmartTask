package com.example.uthsmarttask.ui.screens.login


import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttask.R
import com.example.uthsmarttask.data.model.User
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                if (result.credential is CustomCredential) {
                    val custom = result.credential as CustomCredential
                    if (custom.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(custom.data)
                        val token = googleIdTokenCredential.idToken

                        val credential = GoogleAuthProvider.getCredential(token, null)
                        auth.signInWithCredential(credential)
                            .addOnSuccessListener { authResult ->
                                val user = authResult.user
                                onSuccess(
                                    User(
                                        uid = user?.uid,
                                        name = user?.displayName,
                                        email = user?.email,
                                        photoUrl = user?.photoUrl?.toString()
                                    )
                                )
                            }
                            .addOnFailureListener { e ->
                                onFailure("Đăng nhập thất bại: ${e.localizedMessage}")
                            }
                    }
                }
            } catch (e: NoCredentialException) {
                launcher?.launch(getIntentToAddAccount())
            } catch (e: GetCredentialException) {
                e.printStackTrace()
                onFailure("Lỗi: ${e.localizedMessage}")
            }
        }
    }

    private fun getIntentToAddAccount(): Intent {
        return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        }
    }
}
