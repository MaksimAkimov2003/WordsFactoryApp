package com.akimov.domain.training.useCase.words

import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.model.WordInfoDto

class GetWordByIdUseCase(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(wordId: String): WordInfoDto =
        wordsRepository.getWordById(wordId = wordId)
}