package com.example.tableofannouncements.ui.sign.accounthelper

import android.widget.Toast
import com.example.tableofannouncements.ui.MainActivity
import com.example.tableofannouncements.utils.FirebaseAuthConstants
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class AccountErrorsHelper(act: MainActivity) {
    private val activity = act

    fun handleUserCollisionException(exception: FirebaseAuthUserCollisionException) {
        if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
            Toast.makeText(
                activity,
                FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun handleInvalidCredentialsException(exception: FirebaseAuthInvalidCredentialsException) {
        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
            Toast.makeText(
                activity,
                FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                Toast.LENGTH_LONG
            ).show()
        } else if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
            Toast.makeText(
                activity,
                FirebaseAuthConstants.ERROR_WEAK_PASSWORD,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun handleFirebaseAuthInvalidCredentialsException(exception: FirebaseAuthInvalidCredentialsException) {
        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
            Toast.makeText(
                activity,
                FirebaseAuthConstants.ERROR_INVALID_EMAIL,
                Toast.LENGTH_LONG
            ).show()
        } else if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_CREDENTIAL) {
            Toast.makeText(
                activity,
                FirebaseAuthConstants.ERROR_INVALID_CREDENTIAL,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}