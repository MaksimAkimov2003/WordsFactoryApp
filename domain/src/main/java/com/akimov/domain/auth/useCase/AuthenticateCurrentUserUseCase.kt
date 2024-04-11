package com.akimov.domain.auth.useCase

import com.akimov.domain.auth.repository.AuthRepository

class AuthenticateCurrentUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val (email, password) = repository.getSavedEmailAndPassword()

        if (email == null || password == null) return false

        return repository.login(email, password)
    }
}