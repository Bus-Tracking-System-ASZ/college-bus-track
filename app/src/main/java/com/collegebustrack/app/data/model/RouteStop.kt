package com.collegebustrack.app.data.model

data class RouteStop(
    val name: String = "",
    val time: String = "",
    val state: StopState = StopState.UPCOMING
)
