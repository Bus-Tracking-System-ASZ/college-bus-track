package com.collegebustrack.app.data.model

data class Schedule(
    val id: String = "",
    val routeId: String = "",
    val dayOfWeek: String = "",
    val departureTime: String = "",
    val active: Boolean = true
)