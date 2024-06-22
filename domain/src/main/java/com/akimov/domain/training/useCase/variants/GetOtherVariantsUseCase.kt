package com.akimov.domain.training.useCase.variants

import com.akimov.domain.dictionary.repository.WordsRepository

class GetOtherVariantsUseCase(
    private val repository: WordsRepository
) {
    private companion object {
        private const val OTHER_VARIANTS_COUNT = 2
    }

    suspend operator fun invoke(rightVariantName: String): List<String> =
        repository.getRandomWords(excludedName = rightVariantName, limit = OTHER_VARIANTS_COUNT)
}