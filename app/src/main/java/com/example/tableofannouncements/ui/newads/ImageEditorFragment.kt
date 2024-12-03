package com.example.tableofannouncements.ui.newads

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentImageEditorBinding
import com.example.tableofannouncements.models.SelectImageItem
import com.example.tableofannouncements.ui.newads.adapter.SelectImageAdapter
import com.example.tableofannouncements.utils.ItemTouchMoveCallback

class ImageEditorFragment : Fragment() {
    private var _binding: FragmentImageEditorBinding? = null
    private val binding get() = _binding!!

    private val adapter = SelectImageAdapter()
    private val callback = ItemTouchMoveCallback(adapter)
    private val touchHelper = ItemTouchHelper(callback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchPhotoPicker(3)
        initMenu()
    }
    private fun initMenu(){
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.accept_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.id_accept -> {
                        Toast.makeText(context, "Action clicked!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun launchPhotoPicker(imageCounter: Int) {
        val pickMultipleMedia = this.registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                imageCounter
            )
        ) { uris ->
            if (uris.isNotEmpty()) {
                val imagesStringList = uris.map { it.toString() }
                initAdapter(imagesStringList)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }

    private fun initAdapter(list: List<String>){
        touchHelper.attachToRecyclerView(binding.rvPhotoEditor)
        binding.rvPhotoEditor.layoutManager = LinearLayoutManager(activity)
        binding.rvPhotoEditor.adapter = adapter
        val updatedList = ArrayList<SelectImageItem>()
        for(element in list.indices){
            updatedList.add(SelectImageItem(list[element]))
        }
        adapter.updateAdapter(updatedList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}