package com.collegebustrack.app.data.model

data class BusLocation(
    val busId: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val heading: Double = 0.0,
    val timestamp: Long = 0L
)