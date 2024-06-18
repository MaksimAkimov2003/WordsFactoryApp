package com.akimov.wordsfactory.di

import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountUseCase
import com.akimov.domain.training.useCase.ObserveTimerUseCase
import com.akimov.wordsfactory.screens.training.presentation.TrainingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val trainingModule =
    module {
        factoryOf(::GetWordsInDictionaryCountUseCase)
        factoryOf(::ObserveTimerUseCase)

        viewModelOf(::TrainingViewModel)
    }
