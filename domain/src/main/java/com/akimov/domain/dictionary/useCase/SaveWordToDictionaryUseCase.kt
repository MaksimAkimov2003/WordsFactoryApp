package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.model.WordWithMeaningsDto
import com.akimov.domain.dictionary.repository.WordsRepository

class SaveWordToDictionaryUseCase(
    private val repository: WordsRepository
) {
    suspend operator fun invoke(word: WordWithMeaningsDto): Result<Unit> {
        return if (repository.getWordsCount(word.word) > 0) {
            Result.failure(Exception("Word already exists in the dictionary"))
        } else {
            runCatching {
                repository.saveWordToDictionary(word)
            }
        }
    }

}
