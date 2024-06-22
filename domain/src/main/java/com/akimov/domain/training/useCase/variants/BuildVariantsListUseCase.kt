package com.akimov.domain.training.useCase.variants

class BuildVariantsListUseCase {
    operator fun invoke(incorrectVariants: List<String>, correctVariant: String) =
        buildList {
            add(correctVariant)
            addAll(incorrectVariants)
            shuffled()
        }
}