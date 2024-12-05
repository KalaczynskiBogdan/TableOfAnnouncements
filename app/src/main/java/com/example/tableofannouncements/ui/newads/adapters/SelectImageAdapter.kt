package com.example.tableofannouncements.ui.newads.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.utils.ItemTouchMoveCallback

class SelectImageAdapter : RecyclerView.Adapter<SelectImageAdapter.ImageHolder>(), ItemTouchMoveCallback.OnItemTouch {
    val mainArray = ArrayList<String>()

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var image: ImageView
        fun setData(item: String) {
            image = itemView.findViewById(R.id.ivImage)
            image.setImageURI(Uri.parse(item))
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

    override fun onMove(startPos: Int, targetPos: Int) {
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        mainArray[startPos] = targetItem
        notifyItemMoved(startPos, targetPos)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: List<String>){
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }


}