package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.domain.dictionary.repository.WordsRepository

class SaveWordToDictionaryUseCase(
    private val repository: WordsRepository
) {
    suspend operator fun invoke(word: WordInfoDto): Result<Unit> = runCatching {
        repository.saveWordToDictionary(word)
    }
}
