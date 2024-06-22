package com.akimov.wordsfactory.di

import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountUseCase
import com.akimov.domain.training.interactor.GetQuestionForWordInteractor
import com.akimov.domain.training.useCase.timer.ObserveTimerUseCase
import com.akimov.domain.training.useCase.variants.BuildVariantsListUseCase
import com.akimov.domain.training.useCase.variants.GetOtherVariantsUseCase
import com.akimov.domain.training.useCase.variants.GetRightVariantIndexUseCase
import com.akimov.domain.training.useCase.variants.ValidateVariantsUseCase
import com.akimov.domain.training.useCase.words.GetTittleUseCase
import com.akimov.domain.training.useCase.words.GetWordsForTrainingUseCase
import com.akimov.wordsfactory.screens.question.wrapper.QuestionsWrapperViewModel
import com.akimov.wordsfactory.screens.training.presentation.TrainingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val trainingModule =
    module {
        factoryOf(::GetWordsInDictionaryCountUseCase)
        factoryOf(::ObserveTimerUseCase)
        factoryOf(::GetWordsForTrainingUseCase)
        factoryOf(::GetTittleUseCase)
        factoryOf(::GetOtherVariantsUseCase)
        factoryOf(::ValidateVariantsUseCase)
        factoryOf(::BuildVariantsListUseCase)
        factoryOf(::GetRightVariantIndexUseCase)
        factoryOf(::GetQuestionForWordInteractor)

        viewModelOf(::TrainingViewModel)
        viewModelOf(::QuestionsWrapperViewModel)
    }
