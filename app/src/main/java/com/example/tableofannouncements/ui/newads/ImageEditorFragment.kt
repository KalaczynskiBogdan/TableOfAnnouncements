package com.example.tableofannouncements.ui.newads

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentImageEditorBinding
import com.example.tableofannouncements.ui.newads.adapters.SelectImageAdapter
import com.example.tableofannouncements.utils.ItemTouchMoveCallback
import com.example.tableofannouncements.utils.SharedPreferences

class ImageEditorFragment : Fragment() {
    private var _binding: FragmentImageEditorBinding? = null
    private val binding get() = _binding!!

    private val adapter = SelectImageAdapter()

    private val callback = ItemTouchMoveCallback(adapter)
    private val touchHelper = ItemTouchHelper(callback)

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SharedPreferences(requireContext())
        initMenu()
        launchPhotoPicker(3)
    }

    private fun initMenu(){
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.accept_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.id_accept -> {
                        val sharedPreferences = SharedPreferences(requireContext())
                        sharedPreferences.saveStringList("listForViewPager", adapter.mainArray)
                        findNavController().navigate(
                            R.id.action_imageEditorFragment3_to_addNewAdsFragment,
                            null,
                            NavOptions.Builder()
                                .setPopUpTo(R.id.imageEditorFragment, true)
                                .build()
                        )
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun launchPhotoPicker(count: Int) {
        val pickMultipleMedia = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(count)
        ) { uris ->
            if (uris.isNotEmpty()) {
                uris.forEach { uri ->
                    try {
                        requireContext().contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: SecurityException) {
                        Log.e("PhotoPicker", "Failed to take persistable permission for URI: $uri", e)
                    }
                }
                val imagesStringList = uris.map { it.toString() }
                initAdapter(imagesStringList)
            } else {
                findNavController().navigate(
                    R.id.action_imageEditorFragment3_to_addNewAdsFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.imageEditorFragment, true)
                        .build()
                )
            }
        }

        pickMultipleMedia.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        )
    }

    private fun initAdapter(list: List<String>){
        touchHelper.attachToRecyclerView(binding.rvPhotoEditor)
        binding.rvPhotoEditor.layoutManager = LinearLayoutManager(activity)
        binding.rvPhotoEditor.adapter = adapter
        val updatedList = ArrayList<String>()
        for(element in list.indices){
            updatedList.add(list[element])
        }
        adapter.updateAdapter(updatedList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}