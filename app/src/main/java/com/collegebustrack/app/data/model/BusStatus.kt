package com.collegebustrack.app.data.model

data class BusStatus(
    val busName: String = "",
    val eta: String = "",
    val destination: String = "",
    val seatsAvailable: Int = 0,
    val totalSeats: Int = 0,
    val driver: String = "",
    val rating: String = "0.0"
)
