package com.collegebustrack.app.data.model

data class Notification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val routeId: String? = null,
    val severity: String = "INFO",
    val createdAt: Long = 0L
)