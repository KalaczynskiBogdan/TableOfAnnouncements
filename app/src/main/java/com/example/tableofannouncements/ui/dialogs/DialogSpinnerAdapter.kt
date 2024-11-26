package com.example.tableofannouncements.ui.dialogs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.ui.AddNewAdsFragment

class DialogSpinnerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<DialogSpinnerAdapter.SpViewHolder>() {
    private val mainList = ArrayList<String>()

    class SpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun setData(text: String) {
            val tvSpItem = itemView.findViewById<TextView>(R.id.tvSpItem)
            tvSpItem.text = text
        }

        override fun onClick(v: View?) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sp_list_item, parent, false)
        return SpViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpViewHolder, position: Int) {
        val text = mainList[position]
        holder.setData(text)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(position, text)
        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: ArrayList<String>) {
        mainList.clear()
        mainList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, name: String)
    }

}