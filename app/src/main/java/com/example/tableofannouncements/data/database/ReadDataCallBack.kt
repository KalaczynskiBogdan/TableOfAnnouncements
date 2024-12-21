package com.example.tableofannouncements.data.database

import com.example.tableofannouncements.models.announcement.Announcement

interface ReadDataCallBack {
    fun getData(list: List<Announcement>)
}