package com.akimov.wordsfactory.screens.onBoarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class OnBoardingViewModel: ViewModel() {
    private val _effect = MutableSharedFlow<OnBoardingScreenActions>()
    val effect = _effect.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun onNavigateNextClick() {
        viewModelScope.launch { _effect.emit(OnBoardingScreenActions.NavigateNext) }
    }

    fun onSkipClick() {
        viewModelScope.launch { _effect.emit(OnBoardingScreenActions.Skip) }
    }
}