package com.collegebustrack.app.data.repository

import com.collegebustrack.app.data.model.Route
import com.collegebustrack.app.data.model.Stop
import kotlinx.coroutines.flow.Flow

interface RouteRepository {

    fun observeActiveRoutes(): Flow<List<Route>>

    fun observeRoute(routeId: String): Flow<Route?>

    fun observeStops(): Flow<List<Stop>>

    fun observeStop(stopId: String): Flow<Stop?>

    fun observeStopsByIds(stopIds: List<String>): Flow<List<Stop>>
}
