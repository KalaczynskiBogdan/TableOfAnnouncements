package com.example.tableofannouncements.ui.newads.helpers.validators

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import com.example.tableofannouncements.R
import com.google.android.material.textfield.TextInputEditText

class PriceValidator(
    private val priceText: TextInputEditText,
    private val priceStatus: ImageView,
) {

    fun setupValidation() {
        priceText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val sanitizedText = s.toString().trim()

                if (sanitizedText.contains(" ")) {
                    priceStatus.setImageResource(R.drawable.ic_wrong)
                    priceStatus.visibility = View.VISIBLE
                } else {
                    priceStatus.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (text.startsWith(" ")) {
                    priceText.setText(text.trimStart())
                    priceText.setSelection(priceText.text?.length ?: 0)
                }
                if (isValid()){
                    priceStatus.setImageResource(R.drawable.ic_done)
                    priceStatus.visibility = View.VISIBLE
                }else{
                    priceStatus.setImageResource(R.drawable.ic_wrong)
                    priceStatus.visibility = View.VISIBLE
                }
            }
        })
    }

    fun isValid(): Boolean {
        val text = priceText.text.toString()
        val regex = Regex("^0(\\.\\d{0,2})?$|^[1-9]\\d*(\\.\\d{0,2})?$")
        val price = regex.matches(text)
        return text.isNotEmpty() && price
    }
}