package com.example.tableofannouncements.accounthelper

import android.util.Log
import android.widget.Toast
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.tableofannouncements.ui.MainActivity
import com.example.tableofannouncements.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountHelper(act: MainActivity) {
    private val activity = act
    private val credentialManager = CredentialManager.create(activity)
    private val accountErrorsHelper = AccountErrorsHelper(activity)

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result.user!!)
                        activity.uiUpdate(task.result.user)
                    } else {
                        when (val exception = task.exception) {
                            is FirebaseAuthUserCollisionException -> {
                                linkEmailToGoogle(email, password)
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                accountErrorsHelper.handleInvalidCredentialsException(exception)
                            }

                            else -> {
                                Log.e("Error", "Unknown exception: ${exception?.message}")
                            }
                        }
                    }
                }
        }
    }


    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        activity.uiUpdate(task.result.user)
                    } else {
                        when (val exception = task.exception) {
                            is FirebaseAuthInvalidCredentialsException ->
                                accountErrorsHelper.handleFirebaseAuthInvalidCredentialsException(
                                    exception
                                )
                            else -> Log.d("Error", "Unknown exception: ${exception?.message}")
                        }
                    }
                }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.email_verification_message),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.email_verification_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun linkEmailToGoogle(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        if (activity.myAuth.currentUser != null) {
            activity.myAuth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.link_done),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.enter_for_ling_to_Google),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .setAutoSelectEnabled(false).build()
            )
            .build()
        return credentialManager.getCredential(activity, request)
    }

    suspend fun signInWithGoogle() {
        if (activity.myAuth.currentUser != null) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "You are already signed in", Toast.LENGTH_LONG).show()
            }
        } else {
            val result = buildCredentialRequest()
            handleSignIn(result)
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential
        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val authCredential = GoogleAuthProvider.getCredential(
                tokenCredential.idToken, null
            )
            val authResult =
                activity.myAuth.signInWithCredential(authCredential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "You successfully signed in with Google account",
                            Toast.LENGTH_LONG
                        ).show()
                        activity.uiUpdate(task.result.user)
                    } else {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                }.await()
            return authResult.user != null
        } else {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show()
            return false
        }
    }
    suspend fun signOutFromGoogle() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}