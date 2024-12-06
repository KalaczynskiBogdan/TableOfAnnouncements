package com.example.tableofannouncements.ui.newads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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
    private lateinit var pickMultipleMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var fabStateAdd = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pickMultipleMedia = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia()
        ) { uris ->
            if (uris.isNotEmpty()) {
                uris.forEach { uri ->
                    try {
                        requireContext().contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: SecurityException) {
                        Log.e(
                            "PhotoPicker",
                            "Failed to take persistable permission for URI: $uri",
                            e
                        )
                    }
                }
                val imagesStringList = uris.map { it.toString() }
                addToAdapter(imagesStringList)
            } else {
                if (adapter.mainArray.isEmpty()) {
                    findNavController().navigate(
                        R.id.action_imageEditorFragment3_to_addNewAdsFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.imageEditorFragment, true)
                            .build()
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SharedPreferences(requireContext())
        initAdapter()
        initMenu()
        if (adapter.mainArray.size < 5) {
            launchPhotoPicker()
        }
        setListeners()
    }

    private fun setListeners() {
        binding.fabAddImage.setOnClickListener {
            if (fabStateAdd) {
                launchPhotoPicker()
            } else {
                adapter.clearSelectedItems()
                fabStateAdd = true
                binding.fabAddImage.setImageResource(R.drawable.icon_add)
            }
        }
    }

    private fun initMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.id_edit_accept -> {
                        if (adapter.mainArray.size > 5) {
                            Toast.makeText(
                                requireContext(),
                                "Максимальное количество - 5 фото, пожалуйста удалите ненужное",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            sharedPreferences.saveStringList("listForViewPager", adapter.mainArray)
                            findNavController().navigate(
                                R.id.action_imageEditorFragment3_to_addNewAdsFragment,
                                null,
                                NavOptions.Builder()
                                    .setPopUpTo(R.id.imageEditorFragment, true)
                                    .build()
                            )
                        }
                        true
                    }

                    R.id.id_edit_edit -> {
                        val popupMenu = PopupMenu(
                            requireContext(),
                            requireActivity().findViewById(R.id.id_edit_edit)
                        )
                        popupMenu.menuInflater.inflate(R.menu.delete_menu_item, popupMenu.menu)
                        popupMenu.setOnMenuItemClickListener { subItem ->
                            when (subItem.itemId) {
                                R.id.id_delete -> {
                                    adapter.clearAdapter()
                                    true
                                }

                                R.id.id_selectForDeleting -> {
                                    adapter.showCheckboxes()
                                    fabStateAdd = false
                                    binding.fabAddImage.setImageResource(R.drawable.item_delete)
                                    true
                                }

                                else -> false
                            }
                        }
                        popupMenu.show()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun launchPhotoPicker() {
        pickMultipleMedia.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        )
    }

    private fun initAdapter() {
        touchHelper.attachToRecyclerView(binding.rvPhotoEditor)
        binding.rvPhotoEditor.layoutManager = LinearLayoutManager(activity)
        binding.rvPhotoEditor.adapter = adapter

        val savedList = sharedPreferences.getStringList("listForViewPager")
        Log.d("SavedList", savedList.toString())

        if (savedList.isNotEmpty()) {
            adapter.updateAdapter(savedList)
        }
    }

    private fun addToAdapter(list: List<String>) {
        adapter.updateAdapter(list)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}