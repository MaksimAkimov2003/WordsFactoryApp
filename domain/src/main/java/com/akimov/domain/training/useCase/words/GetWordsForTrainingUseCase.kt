package com.akimov.domain.training.useCase.words

import com.akimov.domain.dictionary.repository.WordsRepository

class GetWordsForTrainingUseCase(
    private val wordsRepository: WordsRepository
) {
    private companion object {
        const val MAX_WORDS_COUNT = 10
    }

    suspend operator fun invoke() =
        wordsRepository.getSortedByCoefficientWords(limit = MAX_WORDS_COUNT)
}