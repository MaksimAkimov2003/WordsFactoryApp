package com.akimov.domain.auth.useCase

import com.akimov.domain.auth.repository.AuthRepository

class LoginUserUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Boolean {
        val isLoginSuccess = repository.login(email = email, password = password)

        if (!isLoginSuccess) return false

        repository.saveEmailAndPassword(email = email, password = password)

        return true
    }

}