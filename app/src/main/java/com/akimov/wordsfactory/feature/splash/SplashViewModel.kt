package com.akimov.wordsfactory.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.auth.useCase.CheckUserAuthUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SplashViewModel(
    private val checkUserAuthUseCase: CheckUserAuthUseCase
) : ViewModel() {
    private val isUserAuthorized = flow {
        emit(checkUserAuthUseCase())
    }

    val state: StateFlow<NextScreen> = isUserAuthorized.map {
        if (it) NextScreen.MAIN
        else NextScreen.ONBOARDING
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NextScreen.UNKNOWN)

}

enum class NextScreen {
    UNKNOWN, MAIN, ONBOARDING
}