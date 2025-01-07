package com.example.tableofannouncements.data.repository

import com.example.tableofannouncements.data.database.DbManager
import com.example.tableofannouncements.domain.models.announcement.Announcement
import com.example.tableofannouncements.domain.repository.AddNewAnnRepository
import javax.inject.Inject

class AddNewAnnRepositoryImpl @Inject constructor(private val db: DbManager) : AddNewAnnRepository {
    override fun publishAnn(announcement: Announcement) {
        db.publishAd(announcement)
    }
}