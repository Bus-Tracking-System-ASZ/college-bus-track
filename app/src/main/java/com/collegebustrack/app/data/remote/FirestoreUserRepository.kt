package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.model.User
import com.collegebustrack.app.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreUserRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserRepository {

    private val authDataSource = FirebaseAuthDataSource(firestore)

    override suspend fun getProfile(uid: String): Result<User> {
        return authDataSource.getProfile(uid)
    }

    override suspend fun createProfile(user: User): Result<Unit> {
        return authDataSource.createProfile(user)
    }

    override suspend fun updateProfile(uid: String, updates: Map<String, Any>): Result<Unit> {
        return authDataSource.updateProfile(uid, updates)
    }

    override suspend fun deleteProfile(uid: String): Result<Unit> {
        return authDataSource.deleteProfile(uid)
    }
}
