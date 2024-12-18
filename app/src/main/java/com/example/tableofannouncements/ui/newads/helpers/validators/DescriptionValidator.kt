package com.example.tableofannouncements.ui.newads.helpers.validators

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tableofannouncements.R
import com.google.android.material.textfield.TextInputEditText

class DescriptionValidator(
    private val descriptionText: TextInputEditText,
    private val descriptionChars: TextView,
    private val descriptionStatus: ImageView
) {

    fun setupValidation() {
        descriptionText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val result = s.toString().replace(" ", "")
                val countOfChars = result.length.toString()
                descriptionChars.text = countOfChars
            }

            override fun afterTextChanged(s: Editable?) {
                if (isValid()){
                    descriptionStatus.setImageResource(R.drawable.ic_done)
                    descriptionStatus.visibility = View.VISIBLE
                }else{
                    descriptionStatus.setImageResource(R.drawable.ic_wrong)
                    descriptionStatus.visibility = View.VISIBLE
                }
            }
        })
    }

    fun isValid(): Boolean {
        val text = descriptionText.text.toString().replace(" ", "")
        return text.length in 30..1000
    }
}