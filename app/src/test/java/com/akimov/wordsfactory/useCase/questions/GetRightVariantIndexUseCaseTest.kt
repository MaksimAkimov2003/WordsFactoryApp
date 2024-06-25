package com.akimov.wordsfactory.useCase.questions

import com.akimov.domain.training.useCase.variants.GetRightVariantIndexUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRightVariantIndexUseCaseTest {

    private val useCase = GetRightVariantIndexUseCase()

    @Test
    fun `when right variant is in the list, should return its index`() {
        val variants = listOf("variant1", "variant2", "rightVariant", "variant3")
        val rightVariant = "rightVariant"
        val result = useCase.invoke(variants, rightVariant)

        assertEquals(2, result)
    }
}