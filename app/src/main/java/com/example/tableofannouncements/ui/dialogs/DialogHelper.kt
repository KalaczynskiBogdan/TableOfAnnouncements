package com.example.tableofannouncements.ui.dialogs

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tableofannouncements.ui.MainActivity
import com.example.tableofannouncements.R
import com.example.tableofannouncements.accounthelper.AccountHelper
import com.example.tableofannouncements.databinding.SignDialogBinding
import com.example.tableofannouncements.utils.DialogConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogHelper(act: MainActivity) {
    private val activity = act
    val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        val view = binding.root
        builder.setView(view)

        setDialogState(index, binding)

        val dialog = builder.create()

        binding.btnSign.setOnClickListener { setOnClickSign(index, binding, dialog) }
        binding.btnForgetPassword.setOnClickListener { setOnClickForgetPassword(binding, dialog) }
        binding.btnSignInWithGoogle.setOnClickListener {setOnClickSignInGoogle(dialog) }
        dialog.show()
    }

    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_up)
            binding.btnSign.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_in)
            binding.btnSign.text = activity.resources.getString(R.string.sign_in_action)
            binding.btnForgetPassword.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSign(index: Int, binding: SignDialogBinding, dialog: AlertDialog) {
        if (index == DialogConst.SIGN_UP_STATE) {
            dialog.dismiss()
            accHelper.signUpWithEmail(
                binding.etSignEmail.text.toString(),
                binding.etSignPassword.text.toString()
            )
        } else {
            dialog.dismiss()
            accHelper.signInWithEmail(
                binding.etSignEmail.text.toString(),
                binding.etSignPassword.text.toString()
            )
        }
    }

    private fun setOnClickForgetPassword(binding: SignDialogBinding, dialog: AlertDialog) {
        if (binding.etSignEmail.text.toString().isNotEmpty()) {
            activity.myAuth.sendPasswordResetEmail(binding.etSignEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.password_reset_message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.password_reset_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            dialog.dismiss()
        } else {
            binding.apply {
                tvEnterEmail.visibility = View.VISIBLE
            }
        }
    }

    private fun setOnClickSignInGoogle(dialog: AlertDialog) {
        CoroutineScope(Dispatchers.IO).launch {
            accHelper.signInWithGoogle()
        }
        dialog.dismiss()
    }
}