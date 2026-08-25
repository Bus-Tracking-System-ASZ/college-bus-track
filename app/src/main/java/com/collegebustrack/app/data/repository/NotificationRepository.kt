package com.collegebustrack.app.data.repository

import com.collegebustrack.app.data.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun observeNotifications(): Flow<List<Notification>>

    fun observeNotificationsByRoute(routeId: String): Flow<List<Notification>>
}
