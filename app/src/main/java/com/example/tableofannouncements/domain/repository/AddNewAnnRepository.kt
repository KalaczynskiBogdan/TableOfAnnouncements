package com.example.tableofannouncements.domain.repository

import com.example.tableofannouncements.domain.models.announcement.Announcement

interface AddNewAnnRepository {
    fun publishAnn(announcement: Announcement){}
}