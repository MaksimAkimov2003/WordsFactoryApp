package com.akimov.domain.auth.useCase

import com.akimov.domain.auth.repository.AuthRepository
import kotlinx.coroutines.coroutineScope

class RegisterUserUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (!checkEmail(email)) return Result.failure(Exception("Поле email не должно быть пустым"))
        if (!checkPassword(password)) return Result.failure(Exception("Поле password не должно быть пустым"))
        if (!repository.register(
                email,
                password
            )
        ) return Result.failure(Exception("Ошибка регистрации"))

        if (!repository.login(
                email = email,
                password = password
            )
        ) return Result.failure(Exception("Ошибка авторизации"))

        repository.saveEmailAndPassword(email = email, password = password)

        return Result.success(Unit)
    }

    private fun checkEmail(email: String) = email.isNotEmpty() && email.isNotBlank()
    private fun checkPassword(password: String) = password.isNotEmpty() && password.isNotBlank()
}