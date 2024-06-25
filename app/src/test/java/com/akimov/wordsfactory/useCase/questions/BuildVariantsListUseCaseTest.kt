package com.akimov.wordsfactory.useCase.questions

import com.akimov.domain.training.useCase.variants.BuildVariantsListUseCase
import org.junit.Assert.assertTrue
import org.junit.Test

class BuildVariantsListUseCaseTest {

    private val useCase = BuildVariantsListUseCase()

    @Test
    fun `when incorrect variants and correct variant are provided, should return shuffled list with all variants`() {
        val incorrectVariants = listOf("incorrect1", "incorrect2")
        val correctVariant = "correct"
        val result = useCase.invoke(incorrectVariants, correctVariant)

        assertTrue(result.contains(correctVariant))
        assertTrue(result.containsAll(incorrectVariants))
    }
}