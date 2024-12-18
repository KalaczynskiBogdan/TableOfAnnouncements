package com.example.tableofannouncements.ui.newads.helpers.validators

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tableofannouncements.R
import com.example.tableofannouncements.ui.newads.dialogs.DialogSpinnerHelper
import com.example.tableofannouncements.utils.CityHelper

class LocationValidator(
    private val country: TextView,
    private val city: TextView,
    private val locationStatus: ImageView,
    private val context: Context
) {
    fun setupValidation() {
        val dialog = DialogSpinnerHelper()
        country.setOnClickListener {
            val list = CityHelper.getAllCountries(context)
            dialog.showSpinnerDialog(
                context,
                list,
                object : DialogSpinnerHelper.OnItemSelectedListener {
                    override fun onItemSelected(selectedItem: String) {
                        country.text = selectedItem
                        city.text = context.getString(R.string.select_city)
                        updateStatus()
                    }
                })
        }
        city.setOnClickListener {
            val selectedCountry = country.text.toString()
            if (selectedCountry != context.getString(R.string.select_country)) {
                val list = CityHelper.getAllCities(context, selectedCountry)
                dialog.showSpinnerDialog(
                    context,
                    list,
                    object : DialogSpinnerHelper.OnItemSelectedListener {
                        override fun onItemSelected(selectedItem: String) {
                            city.text = selectedItem
                            updateStatus()
                        }
                    })
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.select_country),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun isValid(): Boolean {
        return country.text != context.getString(R.string.select_country) && city.text != context.getString(
            R.string.select_city
        )
    }

    private fun updateStatus() {
        if (isValid()) {
            locationStatus.setImageResource(R.drawable.ic_done)
            locationStatus.visibility = View.VISIBLE
        } else {
            locationStatus.visibility = View.GONE
        }
    }
}
