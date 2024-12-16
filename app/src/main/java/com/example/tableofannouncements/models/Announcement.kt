package com.example.tableofannouncements.models

data class Announcement(
    val title: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val description: String? = null,
    val country: String? = null,
    val city: String? = null,
    val userName: String? = null,
    val userEmail: String? = null,
    val userPhone: String? = null
)
