package com.akimov.wordsfactory.screens.login
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.auth.useCase.LoginUserUseCase
import com.akimov.domain.auth.useCase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<LoginScreenState> = MutableStateFlow(
        LoginScreenState.Content
    )
    val state = _state.asStateFlow()

    private val _actions: MutableSharedFlow<LoginScreenAction> = MutableSharedFlow()
    val actions = _actions.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun acceptIntent(intent: LoginScreenIntent) {
        viewModelScope.launch {
            when (intent) {
                is LoginScreenIntent.OnLoginButtonClick -> {
                    _state.update { LoginScreenState.Loading }
                    if (loginUserUseCase(intent.email, intent.password)) {
                        _actions.emit(LoginScreenAction.NavigateToMainScreen)
                    } else {
                        _actions.emit(LoginScreenAction.ShowSnackBar("Пользователь не найден"))
                    }

                    _state.update { LoginScreenState.Content }
                }
            }
        }
    }
}

sealed class LoginScreenAction {
    data class ShowSnackBar(val message: String) : LoginScreenAction()
    data object NavigateToMainScreen : LoginScreenAction()
}

sealed class LoginScreenState {
    data object Loading : LoginScreenState()
    data object Content : LoginScreenState()
}

sealed class LoginScreenIntent {
    data class OnLoginButtonClick(
        val email: String,
        val password: String
    ) : LoginScreenIntent()
}