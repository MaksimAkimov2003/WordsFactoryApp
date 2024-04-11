package com.akimov.data.auth.repository

import android.content.SharedPreferences
import android.util.Log
import com.akimov.domain.auth.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

private const val EMAIL = "email"
private const val PASSWORD = "password"

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return try {
            firebaseAuth.signInWithEmailAndPassword(
                email, password
            ).await().user != null
        } catch (e: Throwable) {
            Log.d("FBException", e.message.toString())
            false
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(
                email, password
            ).await().user != null
        } catch (e: Throwable) {
            Log.d("FBException", e.message.toString())
            false
        }
    }

    override suspend fun getSavedEmailAndPassword(): Pair<String?, String?> =
        sharedPreferences.run {
            getString(EMAIL, null) to getString(PASSWORD, null)
        }

    override suspend fun saveEmailAndPassword(email: String, password: String) = coroutineScope {
        sharedPreferences.edit().apply {
            putString(EMAIL, email)
            putString(PASSWORD, password)
        }.apply()
    }
}