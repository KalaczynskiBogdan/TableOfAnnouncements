package com.example.tableofannouncements.ui.newads.helpers.validators

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.ui.newads.dialogs.DialogSpinnerHelper

class CategoryValidator(
    private val category: TextView,
    private val categoryStatus: ImageView,
    private val context: Context
) {

    fun setupValidation() {
        val dialog = DialogSpinnerHelper()
        category.setOnClickListener {
            val list =
                context.resources.getStringArray(R.array.category).toMutableList() as ArrayList
            dialog.showSpinnerDialog(
                context,
                list,
                object : DialogSpinnerHelper.OnItemSelectedListener {
                    override fun onItemSelected(selectedItem: String) {
                        category.text = selectedItem
                        updateStatus()
                    }
                })
        }
    }

    fun isValid(): Boolean {
        return category.text.toString() != "Выберите категорию"
    }

    private fun updateStatus() {
        if (isValid()) {
            categoryStatus.setImageResource(R.drawable.ic_done)
            categoryStatus.visibility = View.VISIBLE
        } else {
            categoryStatus.visibility = View.GONE
        }
    }
}