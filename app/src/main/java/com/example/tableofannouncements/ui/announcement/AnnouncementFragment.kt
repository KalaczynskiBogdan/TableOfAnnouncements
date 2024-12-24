package com.example.tableofannouncements.ui.announcement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tableofannouncements.databinding.FragmentAnnouncementBinding

class AnnouncementFragment : Fragment() {
    private var _binding: FragmentAnnouncementBinding? = null
    private val binding get() = _binding!!

    private var key: String? = null
    private var title: String? = null
    private var price: String? = null
    private var currency: String? = null
    private var description: String? = null
    private var category: String? = null
    private var country: String? = null
    private var city: String? = null
    private var authorName: String? = null
    private var authorNumber: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveData()
        setInfo()
    }

    private fun receiveData() {
        arguments?.let {
            key = it.getString(ANN_KEY)
            title = it.getString(ANN_TITLE)
            price = it.getString(ANN_PRICE)
            currency = it.getString(ANN_CURRENCY)
            description = it.getString(ANN_DESCRIPTION)
            category = it.getString(ANN_CATEGORY)
            country = it.getString(ANN_COUNTRY)
            city = it.getString(ANN_CITY)
            authorName = it.getString(ANN_AUTHOR_NAME)
            authorNumber = it.getString(ANN_AUTHOR_NUMBER)
        }
        Log.d("Argumentiki", arguments.toString())
    }

    private fun setInfo() {
        binding.apply {
            tvTitleAnnScr.text = title
            tvPriceAnnScr.text = price
            tvCurrencyAnnScr.text = currency
            tvDescriptionAnnScr.text = description
            tvCategoryReceivedAnnScr.text = category
            tvCountryReceivedAnnScr.text = country
            tvCityReceivedAnnScr.text = city
            tvProfileNameAnnScr.text = authorName
            tvProfileNumberAnnScr.text = authorNumber
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ANN_KEY = "ann_key"
        private const val ANN_TITLE = "ann_title"
        private const val ANN_PRICE = "ann_price"
        private const val ANN_CURRENCY = "ann_currency"
        private const val ANN_DESCRIPTION = "ann_description"
        private const val ANN_CATEGORY = "ann_category"
        private const val ANN_COUNTRY = "ann_country"
        private const val ANN_CITY = "ann_city"
        private const val ANN_AUTHOR_NAME = "ann_author_name"
        private const val ANN_AUTHOR_NUMBER = "ann_author_number"
        fun newInstance(
            key: String,
            title: String,
            price: String,
            currency: String,
            description: String,
            category: String,
            country: String,
            city: String,
            authorName: String,
            authorNumber: String
        ): AnnouncementFragment {
            return AnnouncementFragment().apply {
                arguments = Bundle().apply {
                    putString(ANN_KEY, key)
                    putString(ANN_TITLE, title)
                    putString(ANN_PRICE, price)
                    putString(ANN_CURRENCY, currency)
                    putString(ANN_DESCRIPTION, description)
                    putString(ANN_CATEGORY, category)
                    putString(ANN_COUNTRY, country)
                    putString(ANN_CITY, city)
                    putString(ANN_AUTHOR_NAME, authorName)
                    putString(ANN_AUTHOR_NUMBER, authorNumber)
                }
            }
        }
    }
}