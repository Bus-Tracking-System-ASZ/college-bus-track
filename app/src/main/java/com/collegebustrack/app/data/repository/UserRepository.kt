package com.collegebustrack.app.data.repository

import com.collegebustrack.app.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getProfile(uid: String): Result<User>

    suspend fun createProfile(user: User): Result<Unit>

    suspend fun updateProfile(uid: String, updates: Map<String, Any>): Result<Unit>

    suspend fun deleteProfile(uid: String): Result<Unit>
}
