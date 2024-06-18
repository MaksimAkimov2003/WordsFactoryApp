package com.akimov.wordsfactory.di

import com.akimov.wordsfactory.screens.onBoarding.presentation.OnBoardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel { OnBoardingViewModel() }
}