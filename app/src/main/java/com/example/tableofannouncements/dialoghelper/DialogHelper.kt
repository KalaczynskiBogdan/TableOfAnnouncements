package com.example.tableofannouncements.dialoghelper

import androidx.appcompat.app.AlertDialog
import com.example.tableofannouncements.MainActivity
import com.example.tableofannouncements.R
import com.example.tableofannouncements.accounthelper.AccountHelper
import com.example.tableofannouncements.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val activity = act
    private val accHelper = AccountHelper(activity)

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        val view = binding.root

        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_up)
            binding.btnSignUp.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_in)
            binding.btnSignUp.text = activity.resources.getString(R.string.sign_in_action)
        }
        binding.btnSignUp.setOnClickListener {
            if (index == DialogConst.SIGN_UP_STATE) {
                accHelper.signUpWithEmail(
                    binding.etSignEmail.text.toString(),
                    binding.etSignPassword.text.toString()
                )
            } else {
            }
        }



        builder.setView(view)
        builder.show()


    }


}