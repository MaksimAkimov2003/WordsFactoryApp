package com.akimov.wordsfactory.feature.onBoarding.presentation

sealed class OnBoardingScreenActions {
    data object NavigateNext : OnBoardingScreenActions()
    data object Skip : OnBoardingScreenActions()
}