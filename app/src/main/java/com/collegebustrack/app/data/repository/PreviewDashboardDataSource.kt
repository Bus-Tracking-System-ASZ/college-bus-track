package com.collegebustrack.app.data.repository

import com.collegebustrack.app.data.model.*

class PreviewDashboardDataSource {

    fun getDashboardData(): DashboardData {
        return DashboardData(
            student = StudentProfile(
                name = "Amina Khan",
                course = "B.Sc. Computer Science — 3rd Year",
                email = "amina.khan@college.edu",
                phone = "+92 300 1234567",
                address = "123 University Road, Islamabad",
                passId = "PASS-2024-0847",
                validTill = "30 Jun 2026",
                emergencyName = "Tariq Khan",
                emergencyPhone = "+92 300 7654321"
            ),
            bus = BusStatus(
                busName = "Bus 04 — Blue Line",
                eta = "8 min",
                destination = "Main Campus Gate 2",
                seatsAvailable = 12,
                totalSeats = 40,
                driver = "Rashid Ahmed",
                rating = "4.5"
            ),
            stops = listOf(
                RouteStop("Sector H-9 Stop", "7:30 AM", StopState.COMPLETE),
                RouteStop("Melody Chowk", "7:42 AM", StopState.COMPLETE),
                RouteStop("Centaurus Mall", "7:55 AM", StopState.CURRENT),
                RouteStop("Main Campus Gate 2", "8:10 AM", StopState.UPCOMING)
            ),
            alerts = listOf(
                Alert("Route Delayed", "Blue Line is running 5 min behind schedule due to traffic.", "10 min ago"),
                Alert("Schedule Update", "Friday schedule will resume from next week.", "2 hours ago")
            )
        )
    }
}
