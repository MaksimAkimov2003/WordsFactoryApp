package com.akimov.wordsfactory.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.auth.useCase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        false
    )
    val isLoading = _isLoading.asStateFlow()

    private val _actions: MutableSharedFlow<RegisterScreenAction> = MutableSharedFlow()
    val actions = _actions.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun onRegisterButtonClick(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.update { true }
            registerUserUseCase(email, password)
                .onSuccess {
                    _actions.emit(RegisterScreenAction.NavigateToMainScreen)
                }
                .onFailure { error ->
                    _actions.emit(
                        RegisterScreenAction.ShowSnackBar(
                            error.message ?: "Ошибка регистрации"
                        )
                    )
                }

            _isLoading.update { false }
        }
    }

}

sealed class RegisterScreenAction {
    data class ShowSnackBar(val message: String) : RegisterScreenAction()
    data object NavigateToMainScreen : RegisterScreenAction()
}