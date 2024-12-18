package com.example.tableofannouncements.ui.newads.helpers

import android.content.Context
import com.example.tableofannouncements.databinding.FragmentAddNewAdsBinding
import com.example.tableofannouncements.ui.newads.helpers.validators.CategoryValidator
import com.example.tableofannouncements.ui.newads.helpers.validators.DescriptionValidator
import com.example.tableofannouncements.ui.newads.helpers.validators.LocationValidator
import com.example.tableofannouncements.ui.newads.helpers.validators.PriceValidator
import com.example.tableofannouncements.ui.newads.helpers.validators.TitleValidator

class MainValidatorHelper(binding: FragmentAddNewAdsBinding, context: Context) {
    private val titleValidator: TitleValidator
    private val priceValidator: PriceValidator
    private val descriptionValidator: DescriptionValidator
    private val categoryValidator: CategoryValidator
    private val locationValidator: LocationValidator

    init {
        binding.apply {
            titleValidator = TitleValidator(etEnteredTitle, tvCountOfChars, icTitleStatus)
            priceValidator = PriceValidator(etEnteredPrice, icPriceStatus)
            descriptionValidator =
                DescriptionValidator(etEnteredDescription, tvCountOfCharsDescr, icDescriptionStatus)
            categoryValidator = CategoryValidator(tvSelectedCategory, icCategoryStatus,context)
            locationValidator = LocationValidator(tvSelectedCountry, tvSelectedCity, icLocationStatus, context)
        }
    }

    fun setupValidation() {
        titleValidator.setupValidation()
        priceValidator.setupValidation()
        descriptionValidator.setupValidation()
        categoryValidator.setupValidation()
        locationValidator.setupValidation()
    }

    fun isAllValid(): Boolean {
        return titleValidator.isValid() && priceValidator.isValid()
                && descriptionValidator.isValid() && categoryValidator.isValid() && locationValidator.isValid()
    }

    fun saveToDatabase() {}
}