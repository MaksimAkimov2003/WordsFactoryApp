package com.akimov.domain.auth.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    suspend fun getSavedEmailAndPassword(): Pair<String?, String?>
    suspend fun saveEmailAndPassword(email: String, password: String)
}