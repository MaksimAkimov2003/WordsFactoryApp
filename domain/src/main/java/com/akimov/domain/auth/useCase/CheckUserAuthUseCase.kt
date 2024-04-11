package com.akimov.domain.auth.useCase

import com.akimov.domain.auth.repository.AuthRepository

class CheckUserAuthUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val (email, password) = repository.getSavedEmailAndPassword()

        return (email != null) && (password != null)
    }
}