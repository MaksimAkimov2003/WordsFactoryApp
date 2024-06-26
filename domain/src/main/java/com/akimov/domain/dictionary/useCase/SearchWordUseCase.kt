package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.model.WordWithMeaningsDto
import com.akimov.domain.dictionary.repository.WordsRepository

class SearchWordUseCase(
    private val repository: WordsRepository
) {
    suspend operator fun invoke(query: String): Result<WordWithMeaningsDto> = runCatching {
        repository.searchWord(query)
    }
}