package com.example.tableofannouncements.domain.repository

import com.example.tableofannouncements.domain.models.announcement.Announcement

interface AnnouncementListRepository {
    suspend fun getAnnouncements():List<Announcement>
}