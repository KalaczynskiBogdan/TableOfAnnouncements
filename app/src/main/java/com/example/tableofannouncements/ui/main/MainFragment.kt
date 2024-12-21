package com.example.tableofannouncements.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tableofannouncements.R
import com.example.tableofannouncements.data.database.DbManager
import com.example.tableofannouncements.data.database.ReadDataCallBack
import com.example.tableofannouncements.databinding.FragmentMainBinding
import com.example.tableofannouncements.models.MainVpImage
import com.example.tableofannouncements.models.announcement.Announcement
import com.example.tableofannouncements.ui.main.adapters.AnnouncementAdapter
import com.example.tableofannouncements.ui.main.adapters.MainVpImageAdapter
import com.example.tableofannouncements.utils.SharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainFragment : Fragment(), ReadDataCallBack {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vpMain: ViewPager2

    private var announcementAdapter: AnnouncementAdapter? = null

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
        initRecycler()
        observe()
    }

    private fun observe(){
        val dbManager = DbManager(this)
        dbManager.getAdFromDb()
    }

    private fun initRecycler() {
        announcementAdapter = AnnouncementAdapter(
            clickEvent = { Toast.makeText(requireContext(), "Click", Toast.LENGTH_LONG).show() }
        )

        binding.apply {
            rvMain.adapter = announcementAdapter
            rvMain.layoutManager = GridLayoutManager(requireContext(), 2)
        }
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

    override fun getData(list: List<Announcement>) {
        announcementAdapter?.updateList(list)
    }
}
