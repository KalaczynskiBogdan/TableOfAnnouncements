package com.example.tableofannouncements.utils

import android.content.Context
import okio.IOException
import org.json.JSONObject
import java.io.InputStream

object CityHelper {

    fun getAllCountries(context: Context): ArrayList<String> {
        val countriesArray = ArrayList<String>()

        try {
            val inputStream: InputStream = context.assets.open("countriesToCities.json")
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObject = JSONObject(jsonFile)
            val countriesNames = jsonObject.names()
            if (countriesNames != null) {
                for (n in 0 until countriesNames.length()) {
                    countriesArray.add(countriesNames.getString(n))
                }
            }

        } catch (e: IOException) {
        }
        return countriesArray
    }

    fun filterListData(list: ArrayList<String>, searchText: String?): ArrayList<String> {
        val tempList = ArrayList<String>()
        tempList.clear()
        if (searchText == null) {
            tempList.add("no result")
            return tempList
        }
        for (selection: String in list) {
            if (selection.lowercase().startsWith(searchText.lowercase())) {
                tempList.add(selection)
            }
        }
        if (tempList.size == 0) {
            tempList.add("no result")
        }
        return tempList
    }

}