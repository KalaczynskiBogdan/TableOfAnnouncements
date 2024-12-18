package com.example.tableofannouncements.ui.newads.helpers.validators

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tableofannouncements.R
import com.google.android.material.textfield.TextInputEditText

class TitleValidator(
    private val titleText: TextInputEditText,
    private val titleChars: TextView,
    private val titleStatus: ImageView
) {

    fun setupValidation() {
        titleText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val result = s.toString().replace(" ", "")
                val countOfChars = result.length.toString()
                titleChars.text = countOfChars
            }

            override fun afterTextChanged(s: Editable?) {
                if (isValid()){
                    titleStatus.setImageResource(R.drawable.ic_done)
                    titleStatus.visibility = View.VISIBLE
                }else{
                    titleStatus.setImageResource(R.drawable.ic_wrong)
                    titleStatus.visibility = View.VISIBLE
                }
            }
        })
    }

    fun isValid(): Boolean {
        val text = titleText.text.toString().replace(" ", "")
        return text.length in 15..60
    }
}
