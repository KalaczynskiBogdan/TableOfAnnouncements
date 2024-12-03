package com.example.tableofannouncements.ui.newads.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.models.SelectImageItem

class SelectImageAdapter : RecyclerView.Adapter<SelectImageAdapter.ImageHolder>() {
    private val mainArray = ArrayList<SelectImageItem>()

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var image: ImageView
        fun setData(item: SelectImageItem) {
            image = itemView.findViewById(R.id.ivImage)
            image.setImageURI(Uri.parse(item.imageUri))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: List<SelectImageItem>){
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}