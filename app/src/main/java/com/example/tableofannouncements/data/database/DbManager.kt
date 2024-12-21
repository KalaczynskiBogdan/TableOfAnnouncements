package com.example.tableofannouncements.data.database

import com.example.tableofannouncements.models.announcement.Announcement
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager(val readDataCallBack: ReadDataCallBack?) {
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

    fun getAdFromDb() {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val announcementList = ArrayList<Announcement>()
                for (item in snapshot.children) {
                    val announcement = item.children.iterator().next().child("announcement")
                        .getValue(Announcement::class.java)
                    if (announcement != null) {
                        announcementList.add(announcement)
                    }
                }
                readDataCallBack?.getData(announcementList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}