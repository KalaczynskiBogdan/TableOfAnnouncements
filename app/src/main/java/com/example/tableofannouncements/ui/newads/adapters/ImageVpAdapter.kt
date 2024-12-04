package com.example.tableofannouncements.ui.newads.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tableofannouncements.R

class ImageVpAdapter: RecyclerView.Adapter<ImageVpAdapter.ImageVpHolder>() {
    private val mainArray = ArrayList<String>()

    class ImageVpHolder(itemView: View): ViewHolder(itemView) {
        private lateinit var imageVp: ImageView

        fun setData(uri: String){
            imageVp = itemView.findViewById(R.id.ivPagerImage)
            imageVp.setImageURI(Uri.parse(uri))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVpHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_vp_item, parent, false)
        return ImageVpHolder(view)
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onBindViewHolder(holder: ImageVpHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<String>){
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}