package com.example.tableofannouncements.ui.addanns

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tableofannouncements.domain.models.announcement.Announcement
import com.example.tableofannouncements.domain.repository.AddNewAnnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAnnViewModel @Inject constructor(private val repository: AddNewAnnRepository) : ViewModel(){
    fun publishAnn(announcement: Announcement) {
        viewModelScope.launch {
            try {
                repository.publishAnn(announcement)
                Log.d("Debug", "Announcement published successfully")
            } catch (e: Exception) {
                Log.e("Error", "Failed to publish announcement: ${e.message}")
            }
        }
    }
}