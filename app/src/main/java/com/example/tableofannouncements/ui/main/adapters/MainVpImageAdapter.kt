package com.example.tableofannouncements.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.models.MainVpImage

class MainVpImageAdapter(private val imageVpArray: ArrayList<MainVpImage>) : RecyclerView.Adapter<MainVpImageAdapter.MainVpImageViewHolder>() {

    class MainVpImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.ivImageMainVp)
        private val textView = view.findViewById<TextView>(R.id.tvMainVpTitle)
        fun setData(mainVpImage: MainVpImage) {
            imageView.setImageResource(mainVpImage.image)
            textView.text = mainVpImage.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVpImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_images_main_vp, parent, false)
        return MainVpImageViewHolder(view)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: MainVpImageViewHolder, position: Int) {
        val realPosition = position % imageVpArray.size
        holder.setData(imageVpArray[realPosition])
    }
}