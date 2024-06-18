package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.repository.WordsRepository

class GetWordsInDictionaryCountUseCase(
    private val repository: WordsRepository,
) {
    suspend operator fun invoke() = repository.getWordsCount()
}
