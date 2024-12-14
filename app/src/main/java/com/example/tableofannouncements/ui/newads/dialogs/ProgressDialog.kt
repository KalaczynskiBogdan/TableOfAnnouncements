package com.example.tableofannouncements.ui.newads.dialogs

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.tableofannouncements.databinding.ProgressDialogBinding

object ProgressDialog {

    fun createSignDialog(fragment: Fragment): AlertDialog {
        val builder = AlertDialog.Builder(fragment.requireActivity())
        val binding = ProgressDialogBinding.inflate(fragment.layoutInflater)
        val view = binding.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}