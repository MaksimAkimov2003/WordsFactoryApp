package com.akimov.wordsfactory.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class SplashViewModel : ViewModel() {
    // Авторизация отключена — всегда переходим на главный экран
    private val _state = MutableStateFlow(NextScreen.MAIN)
    val state: StateFlow<NextScreen> = _state.asStateFlow()
}

enum class NextScreen {
    UNKNOWN, MAIN, ONBOARDING
}