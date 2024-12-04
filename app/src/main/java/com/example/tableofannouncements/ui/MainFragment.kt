package com.example.tableofannouncements.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tableofannouncements.databinding.FragmentMainBinding
import com.example.tableofannouncements.utils.SharedPreferences


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val sharedPreferences = SharedPreferences(requireContext())
        sharedPreferences.clearData("listForViewPager")
        return binding.root
    }

}
