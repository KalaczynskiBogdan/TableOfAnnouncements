package com.example.tableofannouncements.models

data class Announcement(
    val key: String? = null,
    val title: String? = null,
    val price: String? = null,
    val category: String? = null,
    val description: String? = null,
    val country: String? = null,
    val city: String? = null,
    val userName: String? = null,
    val userEmail: String? = null,
    val userPhone: String? = null
)
