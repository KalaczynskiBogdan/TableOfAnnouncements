package com.example.tableofannouncements.data.repository

import com.example.tableofannouncements.data.database.DbManager
import com.example.tableofannouncements.domain.repository.AnnouncementListRepository
import javax.inject.Inject

class AnnouncementListRepositoryImpl @Inject constructor(private val db: DbManager) :
    AnnouncementListRepository {

    override suspend fun getAnnouncements() = db.getAdFromDb()

}