package com.example.tableofannouncements.ui.newads.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.utils.CityHelper

class DialogSpinnerHelper {
    interface OnItemSelectedListener {
        fun onItemSelected(selectedItem: String)
    }

    fun showSpinnerDialog(context: Context, list: ArrayList<String>, callback: OnItemSelectedListener) {
        val dialog = AlertDialog.Builder(context).create()
        val binding = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)

        val sv = binding.findViewById<SearchView>(R.id.svSpinner)

        val adapter = DialogSpinnerAdapter(object : DialogSpinnerAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, name: String) {
                callback.onItemSelected(name)
                dialog.dismiss()
            }
        })
        val rView = binding.findViewById<RecyclerView>(R.id.rvSpView)
        rView.layoutManager = LinearLayoutManager(context)
        rView.adapter = adapter

        dialog.setView(binding)

        adapter.updateAdapter(list)
        setSearchView(adapter, list, sv)
        dialog.show()
    }

    private fun setSearchView(
        adapter: DialogSpinnerAdapter,
        list: ArrayList<String>,
        sV: SearchView?
    ) {
        sV?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val tempList = CityHelper.filterListData(list, newText)
                adapter.updateAdapter(tempList)
                return true
            }
        })
    }
}