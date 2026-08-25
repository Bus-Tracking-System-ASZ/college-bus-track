package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.Notification
import com.collegebustrack.app.data.model.Route
import com.collegebustrack.app.data.model.Schedule
import com.collegebustrack.app.data.model.Stop
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    fun observeRoutes(): Flow<List<Route>> {
        return firestore
            .collection("routes")
            .whereEqualTo("active", true)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Route::class.java)
                }
            }
    }

    fun observeRoute(routeId: String): Flow<Route?> {
        return firestore
            .collection("routes")
            .document(routeId)
            .snapshots()
            .map { document ->
                document.toObject(Route::class.java)
            }
    }

    fun observeStops(): Flow<List<Stop>> {
        return firestore
            .collection("stops")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Stop::class.java)
                }
            }
    }

    fun observeStop(stopId: String): Flow<Stop?> {
        return firestore
            .collection("stops")
            .document(stopId)
            .snapshots()
            .map { document ->
                document.toObject(Stop::class.java)
            }
    }

    fun observeStopsByIds(stopIds: List<String>): Flow<List<Stop>> {
        if (stopIds.isEmpty()) {
            return kotlinx.coroutines.flow.flowOf(emptyList())
        }
        return firestore
            .collection("stops")
            .whereIn("id", stopIds)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Stop::class.java)
                }.sortedBy { it.sequence }
            }
    }

    fun observeNotifications(): Flow<List<Notification>> {
        return firestore
            .collection("notifications")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Notification::class.java)
                }
            }
    }

    fun observeNotificationsByRoute(routeId: String): Flow<List<Notification>> {
        return firestore
            .collection("notifications")
            .whereEqualTo("routeId", routeId)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Notification::class.java)
                }
            }
    }

    fun observeSchedulesByRoute(routeId: String): Flow<List<Schedule>> {
        return firestore
            .collection("schedules")
            .whereEqualTo("routeId", routeId)
            .whereEqualTo("active", true)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull {
                    it.toObject(Schedule::class.java)
                }
            }
    }
}
