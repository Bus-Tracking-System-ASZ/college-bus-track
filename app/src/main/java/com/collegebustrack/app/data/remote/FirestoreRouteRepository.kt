package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.Route
import com.collegebustrack.app.data.model.Stop
import com.collegebustrack.app.data.repository.RouteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class FirestoreRouteRepository(
    private val firestore: FirebaseFirestore
) : RouteRepository {

    private val dataSource = FirestoreDataSource(firestore)

    override fun observeActiveRoutes(): Flow<List<Route>> {
        return dataSource.observeRoutes()
    }

    override fun observeRoute(routeId: String): Flow<Route?> {
        return dataSource.observeRoute(routeId)
    }

    override fun observeStops(): Flow<List<Stop>> {
        return dataSource.observeStops()
    }

    override fun observeStop(stopId: String): Flow<Stop?> {
        return dataSource.observeStop(stopId)
    }

    override fun observeStopsByIds(stopIds: List<String>): Flow<List<Stop>> {
        return dataSource.observeStopsByIds(stopIds)
    }
}
