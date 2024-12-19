package com.example.tableofannouncements.data.database

import com.example.tableofannouncements.models.announcement.Announcement
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager {
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
}