package com.example.tableofannouncements.data.database

import com.example.tableofannouncements.domain.models.announcement.Announcement
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DbManager{
    val db =
        Firebase.database("https://tableofannouncements-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("main")
    private val auth = Firebase.auth

    fun publishAd(announcement: Announcement) {
        if (auth.uid != null) {
            db.child(announcement.key ?: "empty")
                .child(auth.uid!!)
                .child("announcement")
                .setValue(announcement)
        }
    }

    suspend fun getAdFromDb(): List<Announcement> {
        val snapshot = db.get().await()
        val announcementList = ArrayList<Announcement>()
        for (item in snapshot.children) {
            val announcement = item.children.iterator().next().child("announcement")
                .getValue(Announcement::class.java)
            if (announcement != null) {
                announcementList.add(announcement)
            }
        }
        return announcementList
    }
}