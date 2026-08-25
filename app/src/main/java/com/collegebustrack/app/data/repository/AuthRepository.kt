package com.collegebustrack.app.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser>

    suspend fun register(
        email: String,
        password: String
    ): Result<FirebaseUser>

    fun logout()

    fun observeAuthState(): Flow<FirebaseUser?>
}