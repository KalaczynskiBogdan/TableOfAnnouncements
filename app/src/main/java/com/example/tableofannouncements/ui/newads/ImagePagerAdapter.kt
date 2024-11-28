package com.example.tableofannouncements.ui.newads

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.tableofannouncements.R

class ImagePagerAdapter(fragment: Fragment, private val imageUris: List<Uri>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = imageUris.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ImagePickerFragment()
        val bundle = Bundle().apply {
            putString("imageUri", imageUris[position].toString())
        }
        fragment.arguments = bundle
        return fragment
    }
}