package com.collegebustrack.app.data.model

data class Route(
    val id: String = "",
    val name: String = "",
    val active: Boolean = true,
    val stopIds: List<String> = emptyList()
)