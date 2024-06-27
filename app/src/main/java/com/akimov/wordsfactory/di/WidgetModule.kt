package com.akimov.wordsfactory.di

import com.akimov.domain.dictionary.useCase.GetStudiedWordsFlowUseCase
import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountFlowUseCase
import org.koin.dsl.module

val widgetModule = module {
    factory { GetWordsInDictionaryCountFlowUseCase(get()) }
    factory { GetStudiedWordsFlowUseCase(get()) }
}