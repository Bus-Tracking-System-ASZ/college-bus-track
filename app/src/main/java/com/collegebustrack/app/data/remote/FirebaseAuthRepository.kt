package com.collegebustrack.app.data.remote

import com.collegebustrack.app.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun observeAuthState(): Flow<FirebaseUser?> = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener {
            trySend(it.currentUser)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }
}