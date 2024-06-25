package com.akimov.wordsfactory.useCase.questions

import com.akimov.data.words.repository.WordsRepositoryImpl
import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.useCase.variants.ValidateVariantsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ValidateVariantsUseCaseTest {

    private lateinit var wordsRepository: WordsRepository
    private lateinit var useCase: ValidateVariantsUseCase

    @Before
    fun setup() {
        wordsRepository = mock(WordsRepository::class.java)
        useCase = ValidateVariantsUseCase(wordsRepository)
    }

    @Test
    fun `when variants list size is greater than or equal to two, use case should return the same list`() =
        runBlocking {
            val variants = listOf("variant1", "variant2")
            val result = useCase.invoke(variants)
            assertEquals(variants, result)
        }

    @Test
    fun `when variants list size is less than two, use case should return mocked variants`() =
        runBlocking {
            val variants = listOf("variant1")
            val mockedVariants = WordsRepositoryImpl.MOCKED_VARIANTS
            `when`(wordsRepository.getMockedVariants()).thenReturn(mockedVariants)

            val result = useCase.invoke(variants)

            assertEquals(mockedVariants, result)
        }
}
