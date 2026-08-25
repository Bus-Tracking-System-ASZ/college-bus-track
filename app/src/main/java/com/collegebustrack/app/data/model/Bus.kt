package com.collegebustrack.app.data.model

data class Bus(
    val id: String = "",
    val number: String = "",
    val routeId: String = "",
    val driverId: String = "",
    val capacity: Int = 0,
    val seatsAvailable: Int = 0,
    val status: String = "ACTIVE"
)