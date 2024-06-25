package com.akimov.wordsfactory.useCase.questions

import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.useCase.variants.GetOtherVariantsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetOtherVariantsUseCaseTest {

    private lateinit var wordsRepository: WordsRepository
    private lateinit var useCase: GetOtherVariantsUseCase

    @Before
    fun setup() {
        wordsRepository = mock(WordsRepository::class.java)
        useCase = GetOtherVariantsUseCase(wordsRepository)
    }

    @Test
    fun `when repository returns variants, use case should return the same variants`() =
        runBlocking {
            val rightVariantName = "word1"
            val variants = listOf("word2", "word3")
            `when`(
                wordsRepository.getRandomWords(
                    rightVariantName,
                    GetOtherVariantsUseCase.OTHER_VARIANTS_COUNT
                )
            ).thenReturn(variants)

            val result = useCase.invoke(rightVariantName)

            assertEquals(variants, result)
        }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        val rightVariantName = "word1"
        `when`(
            wordsRepository.getRandomWords(
                rightVariantName,
                GetOtherVariantsUseCase.OTHER_VARIANTS_COUNT
            )
        ).thenReturn(emptyList())

        val result = useCase.invoke(rightVariantName)

        assertEquals(emptyList<String>(), result)
    }
}