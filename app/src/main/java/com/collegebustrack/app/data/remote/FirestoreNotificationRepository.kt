package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.Notification
import com.collegebustrack.app.data.repository.NotificationRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class FirestoreNotificationRepository(
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    private val dataSource = FirestoreDataSource(firestore)

    override fun observeNotifications(): Flow<List<Notification>> {
        return dataSource.observeNotifications()
    }

    override fun observeNotificationsByRoute(routeId: String): Flow<List<Notification>> {
        return dataSource.observeNotificationsByRoute(routeId)
    }
}
