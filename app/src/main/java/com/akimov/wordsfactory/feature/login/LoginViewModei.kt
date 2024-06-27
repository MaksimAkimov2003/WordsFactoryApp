package com.akimov.wordsfactory.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.auth.useCase.LoginUserUseCase
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
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        false
    )
    val isLoading = _isLoading.asStateFlow()

    private val _actions: MutableSharedFlow<LoginScreenAction> = MutableSharedFlow()
    val actions = _actions.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun onLoginButtonClick(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.update { true }
            if (loginUserUseCase(email = email, password = password)) {
                _actions.emit(LoginScreenAction.NavigateToMainScreen)
            } else {
                _actions.emit(LoginScreenAction.ShowSnackBar("Пользователь не найден"))
            }

            _isLoading.update { false }
        }

    }
}

sealed class LoginScreenAction {
    data class ShowSnackBar(val message: String) : LoginScreenAction()
    data object NavigateToMainScreen : LoginScreenAction()
}
