package com.example.tableofannouncements.ui.newads.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tableofannouncements.R

class ImageVpAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ViewHolder>() {
    private val mainArray = ArrayList<String>()

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_BUTTON = 1
    }

    class ImageVpHolder(itemView: View) : ViewHolder(itemView) {
        private lateinit var imageVp: ImageView

        fun setData(uri: String) {
            imageVp = itemView.findViewById(R.id.ivPagerImage)
            imageVp.setImageURI(Uri.parse(uri))
        }
    }

    class ButtonHolder(itemView: View) : ViewHolder(itemView) {
        val button: ImageButton = itemView.findViewById(R.id.btnAddNewImage)

        fun setData() {
            button.setImageResource(R.drawable.icon_add)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mainArray.size) TYPE_BUTTON else TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == TYPE_IMAGE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.image_vp_item, parent, false)
            return ImageVpHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_add_image, parent, false)
            return ButtonHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mainArray.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ImageVpHolder) {
            holder.setData(mainArray[position])
        } else if (holder is ButtonHolder) {
            if (mainArray.size >= 5) {
                holder.button.setImageResource(R.drawable.icon_edit)
            } else {
                holder.setData()
            }
            holder.button.setOnClickListener {
                Log.d("ImageVpAdapter", "Button clicked")
                onItemClickListener.onItemClicked()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<String>) {
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked()
    }
}