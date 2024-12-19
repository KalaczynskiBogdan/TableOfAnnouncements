package com.example.tableofannouncements.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.tableofannouncements.R
import com.example.tableofannouncements.databinding.FragmentMainBinding
import com.example.tableofannouncements.models.MainVpImage
import com.example.tableofannouncements.ui.main.adapters.MainVpImageAdapter
import com.example.tableofannouncements.utils.SharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpMain: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val sharedPreferences = SharedPreferences(requireContext())
        sharedPreferences.clearData("listForViewPager")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMainViewPager()
    }

    private fun setMainViewPager() {
        val list = ArrayList<MainVpImage>()
        list.add(MainVpImage(getString(R.string.notebook_banner), R.drawable.item_notebook_vp))
        list.add(MainVpImage(getString(R.string.car_banner), R.drawable.item_car_vp))
        list.add(MainVpImage(getString(R.string.phone_banner), R.drawable.item_phone_vp))

        vpMain = binding.vpMain
        vpMain.adapter = MainVpImageAdapter(list)

        vpMain.setCurrentItem(list.size * 100, false)

        startAutoScroll()
    }

    private fun startAutoScroll() {
        viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
                delay(10000)
                val nextItem = vpMain.currentItem + 1
                vpMain.setCurrentItem(nextItem, true)
            }
        }
    }
}
