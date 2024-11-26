package com.example.tableofannouncements.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        init()
        onClickSelectCountry()
    }

    private fun init() {
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
                    }
                })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}