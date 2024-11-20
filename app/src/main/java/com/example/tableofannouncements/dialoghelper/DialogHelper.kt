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
        builder.setView(view)

        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_up)
            binding.btnSign.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.an_sign_in)
            binding.btnSign.text = activity.resources.getString(R.string.sign_in_action)
        }

        val dialog = builder.create()

        binding.btnSign.setOnClickListener {
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
        dialog.show()
    }
}