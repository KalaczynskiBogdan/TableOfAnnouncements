package com.example.tableofannouncements.ui.newads.edit

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentImageEditorBinding
import com.example.tableofannouncements.ui.adsgoogle.BaseGoogleAdsFragment
import com.example.tableofannouncements.ui.newads.dialogs.ProgressDialog
import com.example.tableofannouncements.ui.newads.adapters.SelectImageAdapter
import com.example.tableofannouncements.utils.ItemTouchMoveCallback
import com.example.tableofannouncements.utils.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageEditorFragment : BaseGoogleAdsFragment() {
    private var adapter = SelectImageAdapter()

    private val callback = ItemTouchMoveCallback(adapter)
    private val touchHelper = ItemTouchHelper(callback)

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pickMultipleMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var fabStateAdd = true
    private lateinit var currentMenu: Menu

    private var _binding: FragmentImageEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageEditorBinding.inflate(inflater, container, false)
        adView = binding.adView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMediaLauncher()
        sharedPreferences = SharedPreferences(requireContext())
        initAdapter()
        initMenu()
        if (adapter.mainArray.isEmpty()) {
            launchPhotoPicker()
        }
        setListeners()
    }

    private fun createMediaLauncher() {
        pickMultipleMedia = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia()
        ) { uris ->
            if (uris.isNotEmpty()) {
                val dialog = ProgressDialog.createSignDialog(this)

                CoroutineScope(Dispatchers.IO).launch {
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

                    withContext(Dispatchers.Main) {
                        addToAdapter(imagesStringList)
                    }
                    dialog.dismiss()
                }
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

    private fun setListeners() {
        binding.fabAddImage.setOnClickListener {
            if (fabStateAdd) {
                launchPhotoPicker()
            } else {
                adapter.clearSelectedItems()
                fabStateAdd = true
                binding.fabAddImage.setImageResource(R.drawable.icon_add)
                showMenuItems()
            }
        }
    }

    private fun initMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_menu, menu)
                currentMenu = menu

                val cancelItem = menu.findItem(R.id.id_cancel)
                val spannableTitle = SpannableString(cancelItem.title)
                spannableTitle.setSpan(
                    ForegroundColorSpan(Color.WHITE),
                    0,
                    spannableTitle.length,
                    0
                )
                cancelItem.title = spannableTitle
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
                                    if (adapter.mainArray.isNotEmpty()) {
                                        adapter.showCheckboxes()
                                        fabStateAdd = false
                                        binding.fabAddImage.setImageResource(R.drawable.item_delete)
                                        hideMenuItems()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Вы еще не добавили фото",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    true
                                }

                                else -> false
                            }
                        }
                        popupMenu.show()
                        true
                    }

                    R.id.id_cancel -> {
                        fabStateAdd = true
                        adapter.hideCheckboxes()
                        binding.fabAddImage.setImageResource(R.drawable.icon_add)
                        showMenuItems()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun hideMenuItems() {
        currentMenu.apply {
            findItem(R.id.id_edit_accept)?.isVisible = false
            findItem(R.id.id_edit_edit)?.isVisible = false
            findItem(R.id.id_cancel)?.isVisible = true
        }

    }

    private fun showMenuItems() {
        currentMenu.apply {
            findItem(R.id.id_edit_accept)?.isVisible = true
            findItem(R.id.id_edit_edit)?.isVisible = true
            findItem(R.id.id_cancel)?.isVisible = false
        }
    }

    private fun launchPhotoPicker() {
        pickMultipleMedia.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
        )
    }

    private fun initAdapter() {
        binding.apply {
            touchHelper.attachToRecyclerView(rvPhotoEditor)
            rvPhotoEditor.layoutManager = LinearLayoutManager(activity)
            rvPhotoEditor.adapter = adapter
        }
        val savedList = sharedPreferences.getStringList("listForViewPager")

        if (savedList.isNotEmpty()) {
            adapter.updateAdapter(savedList)

        }
    }

    private fun addToAdapter(list: List<String>) {
        adapter.updateAdapter(list)
    }

}