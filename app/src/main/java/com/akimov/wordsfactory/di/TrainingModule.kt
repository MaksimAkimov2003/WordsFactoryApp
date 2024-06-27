package com.akimov.wordsfactory.di

import androidx.lifecycle.SavedStateHandle
import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountUseCase
import com.akimov.domain.training.interactor.GetQuestionForWordInteractor
import com.akimov.domain.training.interactor.UpdateWordLearningProgressInteractor
import com.akimov.domain.training.useCase.timer.ObserveTimerUseCase
import com.akimov.domain.training.useCase.variants.BuildVariantsListUseCase
import com.akimov.domain.training.useCase.variants.GetOtherVariantsUseCase
import com.akimov.domain.training.useCase.variants.GetRightVariantIndexUseCase
import com.akimov.domain.training.useCase.variants.ValidateVariantsUseCase
import com.akimov.domain.training.useCase.words.CalculateWordCoefficientUseCase
import com.akimov.domain.training.useCase.words.GetTittleUseCase
import com.akimov.domain.training.useCase.words.GetWordByIdUseCase
import com.akimov.domain.training.useCase.words.GetWordsForTrainingUseCase
import com.akimov.domain.training.useCase.words.UpdateWordCoefficientUseCase
import com.akimov.wordsfactory.feature.question.presentation.QuestionViewModel
import com.akimov.wordsfactory.feature.question.wrapper.presentation.QuestionsWrapperViewModel
import com.akimov.wordsfactory.feature.training.presentation.TrainingViewModel
import com.akimov.wordsfactory.feature.trainingFinished.presentation.TrainingFinishedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
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
        factoryOf(::GetWordByIdUseCase)
        factoryOf(::CalculateWordCoefficientUseCase)
        factoryOf(::UpdateWordCoefficientUseCase)

        factory {
            UpdateWordLearningProgressInteractor(
                coroutineScope = CoroutineScope(Dispatchers.IO),
                getWordByIdUseCase = get(),
                calculateWordCoefficientUseCase = get(),
                updateWordCoefficientUseCase = get()
            )
        }

        viewModelOf(::TrainingViewModel)
        viewModelOf(::QuestionsWrapperViewModel)
        viewModel { (savedStateHandle: SavedStateHandle) ->
            QuestionViewModel(
                observeTimerUseCase = get(),
                getQuestionForWordInteractor = get(),
                savedStateHandle = savedStateHandle
            )
        }
        viewModelOf(::TrainingFinishedViewModel)
    }
