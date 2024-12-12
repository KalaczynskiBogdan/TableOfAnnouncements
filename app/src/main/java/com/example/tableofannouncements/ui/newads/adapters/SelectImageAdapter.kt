package com.example.tableofannouncements.ui.newads.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tableofannouncements.R
import com.example.tableofannouncements.utils.ItemTouchMoveCallback

class SelectImageAdapter : RecyclerView.Adapter<SelectImageAdapter.ImageHolder>(), ItemTouchMoveCallback.OnItemTouch {
    val mainArray = ArrayList<String>()
    private val isCheckboxVisible = mutableListOf<Boolean>()
    private val selectedItems = mutableSetOf<Int>()

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.ivImage)
        private val checkbox: CheckBox = itemView.findViewById(R.id.cbSelectImage)

        fun setData(item: String, isVisible: Boolean, isChecked: Boolean, onCheckedChange: (Int, Boolean) -> Unit) {
            image.setImageURI(Uri.parse(item))
            checkbox.visibility = if (isVisible) View.VISIBLE else View.GONE
            checkbox.isChecked = isChecked
            checkbox.setOnCheckedChangeListener { _, isSelected ->
                onCheckedChange(adapterPosition, isSelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int = mainArray.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(
            mainArray[position],
            isCheckboxVisible[position],
            selectedItems.contains(position)
        ) { pos, isSelected ->
            if (isSelected) {
                selectedItems.add(pos)
            } else {
                selectedItems.remove(pos)
            }
        }
    }

    override fun onMove(startPos: Int, targetPos: Int) {
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        mainArray[startPos] = targetItem

        val targetVisibility = isCheckboxVisible[targetPos]
        isCheckboxVisible[targetPos] = isCheckboxVisible[startPos]
        isCheckboxVisible[startPos] = targetVisibility

        notifyItemMoved(startPos, targetPos)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: List<String>) {
        mainArray.addAll(newList)
        isCheckboxVisible.addAll(List(newList.size) { false })
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAdapter() {
        mainArray.clear()
        isCheckboxVisible.clear()
        selectedItems.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showCheckboxes() {
        for (i in isCheckboxVisible.indices) {
            isCheckboxVisible[i] = true
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun hideCheckboxes() {
        for (i in isCheckboxVisible.indices) {
            isCheckboxVisible[i] = false
        }
        selectedItems.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelectedItems() {
        val indicesToRemove = selectedItems.sortedDescending()
        Log.d("LISTOK", "Indices to remove: $indicesToRemove")

        for (index in indicesToRemove) {
            if (index in mainArray.indices) {
                mainArray.removeAt(index)
                isCheckboxVisible.removeAt(index)
            }
        }

        selectedItems.clear()
        for (i in isCheckboxVisible.indices) {
            isCheckboxVisible[i] = false
        }
        notifyDataSetChanged()
    }
}
