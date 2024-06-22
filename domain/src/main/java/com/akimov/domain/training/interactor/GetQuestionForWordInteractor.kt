package com.akimov.domain.training.interactor

import com.akimov.domain.training.model.QuestionDto
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.domain.training.useCase.variants.BuildVariantsListUseCase
import com.akimov.domain.training.useCase.variants.GetOtherVariantsUseCase
import com.akimov.domain.training.useCase.variants.GetRightVariantIndexUseCase
import com.akimov.domain.training.useCase.variants.ValidateVariantsUseCase
import com.akimov.domain.training.useCase.words.GetTittleUseCase
import kotlinx.collections.immutable.toImmutableList

class GetQuestionForWordInteractor(
    private val getTittleUseCase: GetTittleUseCase,
    private val getOtherVariantsUseCase: GetOtherVariantsUseCase,
    private val validateVariantsUseCase: ValidateVariantsUseCase,
    private val buildVariantsListUseCase: BuildVariantsListUseCase,
    private val getRightVariantIndexUseCase: GetRightVariantIndexUseCase
) {
    suspend operator fun invoke(word: WordTrainingDto): QuestionDto {
        val tittle = getTittleUseCase(definitions = word.definitions)

        val incorrectVariants = getOtherVariantsUseCase(rightVariantName = word.name)

        val validatedIncorrectVariants = validateVariantsUseCase(variants = incorrectVariants)

        val allVariants = buildVariantsListUseCase(
            correctVariant = word.name,
            incorrectVariants = validatedIncorrectVariants
        )

        val indexOfCorrect = getRightVariantIndexUseCase(
            variants = allVariants,
            rightVariant = word.name
        )

        return QuestionDto(
            tittle = tittle,
            variants = allVariants.toImmutableList(),
            correctVariantIndex = indexOfCorrect
        )
    }
}
