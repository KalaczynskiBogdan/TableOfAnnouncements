package com.example.tableofannouncements.ui.newads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentAddNewAdsBinding
import com.example.tableofannouncements.ui.dialogs.DialogSpinnerHelper
import com.example.tableofannouncements.utils.CityHelper

class AddNewAdsFragment : Fragment() {
    private var _binding: FragmentAddNewAdsBinding? = null
    val binding get() = _binding!!
    private val dialog = DialogSpinnerHelper()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewAdsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickSelectCountry()
        onClickSelectCity()
        onClickSelectImages()
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
                Toast.makeText(requireContext(), "No country selected", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun onClickSelectImages() {
        binding.ibAddImager.setOnClickListener {
            findNavController().navigate(R.id.action_addNewAdsFragment_to_imageEditorFragment3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}