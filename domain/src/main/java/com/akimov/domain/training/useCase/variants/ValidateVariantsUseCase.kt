package com.akimov.domain.training.useCase.variants

import com.akimov.domain.dictionary.repository.WordsRepository

class ValidateVariantsUseCase(
    private val repository: WordsRepository
) {
    operator fun invoke(variants: List<String>) = if (variants.size >= 2) {
        variants
    } else {
        repository.getMockedVariants()
    }
}