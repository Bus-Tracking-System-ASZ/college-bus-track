package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource(
    private val firestore: FirebaseFirestore
) {

    private val usersCollection = firestore.collection("users")

    suspend fun createProfile(user: User): Result<Unit> {
        return try {
            usersCollection
                .document(user.uid)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile(uid: String): Result<User> {
        return try {
            val snapshot = usersCollection
                .document(uid)
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(IllegalStateException("User document not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(uid: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersCollection
                .document(uid)
                .update(updates)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteProfile(uid: String): Result<Unit> {
        return try {
            usersCollection
                .document(uid)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
