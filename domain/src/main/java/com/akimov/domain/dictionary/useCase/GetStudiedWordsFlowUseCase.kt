package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.repository.WordsRepository

class GetStudiedWordsFlowUseCase(
    private val repository: WordsRepository
) {
    operator fun invoke() = repository.getStudiedWordsCountFlow()
}