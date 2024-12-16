package com.example.tableofannouncements.ui.newads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentAddNewAdsBinding
import com.example.tableofannouncements.ui.adsgoogle.BaseGoogleAdsFragment
import com.example.tableofannouncements.ui.newads.dialogs.DialogSpinnerHelper
import com.example.tableofannouncements.ui.newads.adapters.ImageVpAdapter
import com.example.tableofannouncements.utils.CityHelper
import com.example.tableofannouncements.utils.SharedPreferences

class AddNewAdsFragment : BaseGoogleAdsFragment() {
    private var _binding: FragmentAddNewAdsBinding? = null
    val binding get() = _binding!!

    private lateinit var imageAdapter: ImageVpAdapter
    private val dialog = DialogSpinnerHelper()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewAdsBinding.inflate(inflater, container, false)
        adView = binding.adView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = SharedPreferences(requireContext())

        initToolbar()
        onClickSelectCountry()
        onClickSelectCity()
        onClickSelectImages()
        onClickSelectCategory()
        initListOfImages()

        onCreateNewAd()
    }

    private fun onCreateNewAd() {
        binding.btnCreateNewAd.setOnClickListener {
            showInterAd()
        }
    }

    private fun initToolbar() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        val titleView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleView.text = getString(R.string.create_ad)
    }

    override fun onClose() {
        super.onClose()
        findNavController().navigate(
            R.id.action_addNewAdsFragment_to_mainFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.mainFragment, true)
                .build()
        )
        sharedPreferences.clearData("listForViewPager")
    }

    private fun initListOfImages() {
        imageAdapter = ImageVpAdapter(object : ImageVpAdapter.OnItemClickListener {
            override fun onItemClicked() {
                findNavController().navigate(R.id.action_addNewAdsFragment_to_imageEditorFragment3)
            }
        })
        binding.vpImages.adapter = imageAdapter

        val receivedList = sharedPreferences.getStringList("listForViewPager")
        if (receivedList.isNotEmpty()) {
            imageAdapter.update(receivedList)
            binding.ibAddImager.visibility = View.GONE
        }
    }

    private fun onClickSelectCountry() {
        val list = CityHelper.getAllCountries(requireContext())
        binding.tvSelectCountry.setOnClickListener {
            dialog.showSpinnerDialog(
                requireContext(),
                list,
                object : DialogSpinnerHelper.OnItemSelectedListener {
                    override fun onItemSelected(selectedItem: String) {
                        binding.tvSelectCountry.text = selectedItem
                        binding.tvSelectCity.text = getString(R.string.select_city)
                    }
                })
        }
    }

    private fun onClickSelectCity() {
        binding.tvSelectCity.setOnClickListener {
            val selectedCountry = binding.tvSelectCountry.text.toString()
            if (selectedCountry != getString(R.string.select_country)) {
                val list = CityHelper.getAllCities(requireContext(), selectedCountry)
                dialog.showSpinnerDialog(
                    requireContext(),
                    list,
                    object : DialogSpinnerHelper.OnItemSelectedListener {
                        override fun onItemSelected(selectedItem: String) {
                            binding.tvSelectCity.text = selectedItem
                        }
                    })
            } else {
                Toast.makeText(requireContext(), getString(R.string.select_country), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onClickSelectCategory() {
        binding.tvSelectCategory.setOnClickListener {
            val list = resources.getStringArray(R.array.category).toMutableList() as ArrayList
            dialog.showSpinnerDialog(requireContext(), list, object : DialogSpinnerHelper.OnItemSelectedListener{
                override fun onItemSelected(selectedItem: String) {
                    binding.tvSelectCategory.text = selectedItem
                }
            })
        }
    }



    private fun onClickSelectImages() {
        binding.ibAddImager.setOnClickListener {
            findNavController().navigate(R.id.action_addNewAdsFragment_to_imageEditorFragment3)
        }
    }


    override fun onDestroyView() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        val titleView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleView.text = ""
        _binding = null
        super.onDestroyView()

    }
}