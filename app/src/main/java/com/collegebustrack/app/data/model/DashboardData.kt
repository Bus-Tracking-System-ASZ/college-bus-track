package com.collegebustrack.app.data.model

data class DashboardData(
    val student: StudentProfile = StudentProfile(),
    val bus: BusStatus = BusStatus(),
    val stops: List<RouteStop> = emptyList(),
    val alerts: List<Alert> = emptyList()
)
