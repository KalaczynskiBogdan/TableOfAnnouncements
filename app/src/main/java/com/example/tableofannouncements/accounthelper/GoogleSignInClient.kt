package com.example.tableofannouncements.accounthelper

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.tableofannouncements.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleSignInClient(private val context: Context) {
    private val tag = "GoogleSignInClient: "
    private val credentialManager = CredentialManager.create(context)
    private val fireBaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean {
        if (fireBaseAuth.currentUser != null) {
            println(tag + "already signed in")
            return true
        } else {
            return false
        }
    }

    suspend fun signIn(): Boolean {
        if (isSignedIn()) {
            return true
        }
        try {
            val result = buildCredentialRequest()
            return handleSignIn(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "signIn error: ${e.message}")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setAutoSelectEnabled(false).build()
            )
            .build()
        return credentialManager.getCredential(context, request)
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential
        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                println(tag + "name: ${tokenCredential.displayName}")
                println(tag + "email: ${tokenCredential.id}")

                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = fireBaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException) {
                println(tag + "GoogleIdTokenParsingException: ${e.message}")
                return false
            }
        } else {
            println(tag + "credential is not GoogleIdTokenCredential")
            return false
        }
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
    }
}