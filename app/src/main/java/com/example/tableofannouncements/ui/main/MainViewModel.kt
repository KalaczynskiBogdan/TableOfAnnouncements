package com.example.tableofannouncements.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tableofannouncements.domain.models.announcement.Announcement
import com.example.tableofannouncements.domain.repository.AnnouncementListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AnnouncementListRepository): ViewModel() {
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val listOfAnnouncementsLiveData = MutableLiveData<List<Announcement>>()

    fun getListOfAnnouncements(){
        viewModelScope.launch(myCoroutineContext) {
            val list = repository.getAnnouncements()
            listOfAnnouncementsLiveData.postValue(list)
        }
    }
}