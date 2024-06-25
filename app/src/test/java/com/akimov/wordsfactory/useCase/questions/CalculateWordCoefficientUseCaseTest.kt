package com.akimov.wordsfactory.useCase.questions

import com.akimov.domain.training.useCase.words.CalculateWordCoefficientUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateWordCoefficientUseCaseTest {

    private val useCase = CalculateWordCoefficientUseCase()

    @Test
    fun `when answer is correct, coefficient should increase by one`() {
        val oldCoefficient = 5
        val newCoefficient = useCase.invoke(oldCoefficient, true)
        assertEquals(6, newCoefficient)
    }

    @Test
    fun `when answer is incorrect, coefficient should decrease by one`() {
        val oldCoefficient = 5
        val newCoefficient = useCase.invoke(oldCoefficient, false)
        assertEquals(4, newCoefficient)
    }
}