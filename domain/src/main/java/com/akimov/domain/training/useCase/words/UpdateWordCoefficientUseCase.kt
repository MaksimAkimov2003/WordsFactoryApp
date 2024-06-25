package com.akimov.domain.training.useCase.words

import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.model.WordInfoDto

class UpdateWordCoefficientUseCase(
    private val repository: WordsRepository
) {
    suspend operator fun invoke(
        word: WordInfoDto,
        newCoefficient: Int
    ) = repository.updateWord(new = word.copy(knowledgeCoefficient = newCoefficient))
}
