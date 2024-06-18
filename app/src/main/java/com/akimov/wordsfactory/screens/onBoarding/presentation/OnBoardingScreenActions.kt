package com.akimov.wordsfactory.screens.onBoarding.presentation

sealed class OnBoardingScreenActions {
    data object NavigateNext : OnBoardingScreenActions()
    data object Skip : OnBoardingScreenActions()
}