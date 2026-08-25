package com.collegebustrack.app.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val classYear: Int = 0,
    val role: String = "STUDENT",
    val passId: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = ""
)