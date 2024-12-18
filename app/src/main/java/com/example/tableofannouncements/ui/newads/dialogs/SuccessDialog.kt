package com.example.tableofannouncements.ui.newads.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tableofannouncements.R

class SuccessDialog {
    fun createDialog(context: Context, fragment: Fragment) {
        val dialog = AlertDialog.Builder(context, R.style.DialogTransparentBackground).create()
        val binding = LayoutInflater.from(context).inflate(R.layout.new_ad_success_dialog, null)

        val button = binding.findViewById<Button>(R.id.btnOkay)

        button.setOnClickListener {
            dialog.dismiss()
            fragment.findNavController().navigate(R.id.action_addNewAdsFragment_to_mainFragment)
        }
        dialog.setView(binding)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }
}