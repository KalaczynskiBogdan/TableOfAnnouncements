package com.example.tableofannouncements.ui.newads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tableofannouncements.R
import com.example.tableofannouncements.data.database.DbManager
import com.example.tableofannouncements.databinding.FragmentAddNewAdsBinding
import com.example.tableofannouncements.models.Announcement
import com.example.tableofannouncements.ui.adsgoogle.BaseGoogleAdsFragment
import com.example.tableofannouncements.ui.newads.dialogs.DialogSpinnerHelper
import com.example.tableofannouncements.ui.newads.adapters.ImageVpAdapter
import com.example.tableofannouncements.ui.newads.helpers.MainValidatorHelper
import com.example.tableofannouncements.utils.SharedPreferences

class AddNewAdsFragment : BaseGoogleAdsFragment() {
    private var _binding: FragmentAddNewAdsBinding? = null
    val binding get() = _binding!!

    private lateinit var imageAdapter: ImageVpAdapter
    private val dialog = DialogSpinnerHelper()
    private val dbManager = DbManager()

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

        initValidators(binding)

        onClickSelectImages()
        initListOfImages()
        onClickCreateNewAd()
        onClickSelectCurrency()
    }


    private fun onClickCreateNewAd() {
        binding.btnCreateNewAd.setOnClickListener {
            //showInterAd()
            onClose()
        }
    }

    override fun onClose() {
        super.onClose()
        sharedPreferences.clearData("listForViewPager")

        val createdAnnouncement = fillAnnouncement()
        dbManager.publishAd(createdAnnouncement)

        findNavController().navigate(
            R.id.action_addNewAdsFragment_to_mainFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.mainFragment, true)
                .build()
        )
    }

    private fun initValidators(binding: FragmentAddNewAdsBinding) {
        val mainValidatorHelper = MainValidatorHelper(binding, requireContext())
        mainValidatorHelper.setupValidation()
    }

    private fun fillAnnouncement(): Announcement {
        val announcement: Announcement
        binding.apply {
            announcement = Announcement(
                dbManager.db.push().key,
                etEnteredTitle.text.toString(),
                etEnteredPrice.text.toString(),
                tvSelectedCategory.text.toString(),
                etEnteredDescription.text.toString(),
                tvSelectedCountry.text.toString(),
                tvSelectedCity.text.toString(),
                etEnteredName.text.toString(),
                etEnteredEmail.text.toString(),
                etEnteredPhone.text.toString(),
            )
        }
        return announcement
    }

    private fun initToolbar() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        val titleView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleView.text = getString(R.string.create_ad)
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

    private fun onClickSelectCurrency() {
        binding.tvCurrencyName.setOnClickListener {
            val list = resources.getStringArray(R.array.currency).toMutableList() as ArrayList
            dialog.showSpinnerDialog(
                requireContext(),
                list,
                object : DialogSpinnerHelper.OnItemSelectedListener {
                    override fun onItemSelected(selectedItem: String) {
                        binding.tvCurrencyName.text = selectedItem
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