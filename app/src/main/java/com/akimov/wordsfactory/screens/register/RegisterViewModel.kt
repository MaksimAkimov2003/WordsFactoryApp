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
    private val _state: MutableStateFlow<RegisterScreenState> = MutableStateFlow(
        RegisterScreenState.Content
    )
    val state = _state.asStateFlow()

    private val _actions: MutableSharedFlow<RegisterScreenAction> = MutableSharedFlow()
    val actions = _actions.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun acceptIntent(intent: RegisterScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                is RegisterScreenIntent.OnRegisterButtonClick -> {
                    _state.update { RegisterScreenState.Loading }
                    registerUserUseCase(intent.email, intent.password)
                        .onSuccess {
                            _actions.emit(RegisterScreenAction.NavigateToMainScreen)
                        }
                        .onFailure { error ->
                            _actions.emit(RegisterScreenAction.ShowSnackBar(error.message ?: "Ошибка регистрации"))
                        }

                    _state.update { RegisterScreenState.Content }
                }
            }
        }
    }
}

sealed class RegisterScreenAction {
    data class ShowSnackBar(val message: String) : RegisterScreenAction()
    data object NavigateToMainScreen : RegisterScreenAction()
}

sealed class RegisterScreenState {
    data object Loading : RegisterScreenState()
    data object Content : RegisterScreenState()
}

sealed class RegisterScreenIntent {
    data class OnRegisterButtonClick(
        val email: String,
        val password: String
    ) : RegisterScreenIntent()
}