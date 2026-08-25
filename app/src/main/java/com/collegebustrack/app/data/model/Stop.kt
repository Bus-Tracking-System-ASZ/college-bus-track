package com.collegebustrack.app.data.model

data class Stop(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val sequence: Int = 0
)