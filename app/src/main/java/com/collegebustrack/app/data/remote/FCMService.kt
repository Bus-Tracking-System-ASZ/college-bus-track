package com.collegebustrack.app.data.remote

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FCMService {

    private val messaging = FirebaseMessaging.getInstance()

    suspend fun requestPermissionAndGetToken(): Result<String> {
        return try {
            val token = messaging.token.await()
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun subscribeToAllUsersTopic(): Result<Unit> {
        return try {
            messaging.subscribeToTopic("all-users").await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun subscribeToRouteTopic(routeId: String): Result<Unit> {
        return try {
            messaging.subscribeToTopic("route-$routeId").await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun unsubscribeFromRouteTopic(routeId: String): Result<Unit> {
        return try {
            messaging.unsubscribeFromTopic("route-$routeId").await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveTokenToFirestore(uid: String): Result<Unit> {
        return try {
            val token = messaging.token.await()
            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            db.collection("deviceTokens")
                .document(uid)
                .set(
                    mapOf(
                        "uid" to uid,
                        "token" to token,
                        "platform" to "android",
                        "updatedAt" to System.currentTimeMillis()
                    )
                )
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
