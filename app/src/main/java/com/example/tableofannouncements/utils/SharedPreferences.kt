package com.example.tableofannouncements.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("listForViewPager", Context.MODE_PRIVATE)

    fun saveStringList(key: String, list: List<String>) {
        val jsonString = list.joinToString(",")
        prefs.edit()?.putString(key, jsonString)?.apply()
    }

    fun getStringList(key: String): List<String> {
        val jsonString = prefs.getString(key, null) ?: return emptyList()
        return jsonString.split(",").filter { it.isNotBlank() }
    }

    fun clearData(key: String) {
        prefs.edit()?.remove(key)?.apply()
    }
}