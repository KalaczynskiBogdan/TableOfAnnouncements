package com.example.tableofannouncements.accounthelper

import android.widget.Toast
import com.example.tableofannouncements.MainActivity
import com.example.tableofannouncements.R
import com.google.firebase.auth.FirebaseUser

class AccountHelper(act: MainActivity) {
    private val activity = act

    fun signUpWithEmail(email: String, password: String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            activity.myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    sendEmailVerification(task.result.user!!)
                }else{
                    Toast.makeText(activity, activity.resources.getString(R.string.sign_up_error), Toast.LENGTH_LONG).show()
                }
            }
        }else{}
    }

    fun signInWithEmail(email: String, password: String){

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

}