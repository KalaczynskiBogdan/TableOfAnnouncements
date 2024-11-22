package com.example.tableofannouncements.accounthelper

import android.widget.Toast
import androidx.credentials.CredentialManager
import com.example.tableofannouncements.MainActivity
import com.example.tableofannouncements.R
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseUser

class AccountHelper(act: MainActivity) {
    private val activity = act

    fun signUpWithEmail(email: String, password: String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            activity.myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    sendEmailVerification(task.result.user!!)
                    activity.uiUpdate(task.result.user)
                }else{
                    Toast.makeText(activity, activity.resources.getString(R.string.sign_up_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            activity.myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    activity.uiUpdate(task.result.user)
                }else{
                    Toast.makeText(activity, activity.resources.getString(R.string.sign_in_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(activity, activity.resources.getString(R.string.email_verification_message), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(activity, activity.resources.getString(R.string.email_verification_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getSignInClient(){
        val credentialManager = CredentialManager.create(activity)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id)).build()

    }
}