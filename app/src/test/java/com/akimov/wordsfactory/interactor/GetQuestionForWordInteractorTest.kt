package com.akimov.wordsfactory.interactor

import com.akimov.domain.training.interactor.GetQuestionForWordInteractor
import com.akimov.domain.training.model.QuestionDto
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.domain.training.useCase.variants.BuildVariantsListUseCase
import com.akimov.domain.training.useCase.variants.GetOtherVariantsUseCase
import com.akimov.domain.training.useCase.variants.GetRightVariantIndexUseCase
import com.akimov.domain.training.useCase.variants.ValidateVariantsUseCase
import com.akimov.domain.training.useCase.words.GetTittleUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetQuestionForWordInteractorTest {

    private lateinit var getTittleUseCase: GetTittleUseCase
    private lateinit var getOtherVariantsUseCase: GetOtherVariantsUseCase
    private lateinit var validateVariantsUseCase: ValidateVariantsUseCase
    private lateinit var buildVariantsListUseCase: BuildVariantsListUseCase
    private lateinit var getRightVariantIndexUseCase: GetRightVariantIndexUseCase
    private lateinit var interactor: GetQuestionForWordInteractor

    @Before
    fun setup() {
        getTittleUseCase = mock(GetTittleUseCase::class.java)
        getOtherVariantsUseCase = mock(GetOtherVariantsUseCase::class.java)
        validateVariantsUseCase = mock(ValidateVariantsUseCase::class.java)
        buildVariantsListUseCase = mock(BuildVariantsListUseCase::class.java)
        getRightVariantIndexUseCase = mock(GetRightVariantIndexUseCase::class.java)
        interactor = GetQuestionForWordInteractor(
            getTittleUseCase,
            getOtherVariantsUseCase,
            validateVariantsUseCase,
            buildVariantsListUseCase,
            getRightVariantIndexUseCase
        )
    }

    @Test
    fun `when all use cases return expected results, interactor should return correct question`() =
        runBlocking {
            val word = WordTrainingDto(
                id = "1",
                name = "word1",
                definitions = listOf("definition1")
            )
            val tittle = "tittle"
            val incorrectVariants = listOf("word2", "word3")
            val validatedIncorrectVariants = listOf("word2", "word3")
            val allVariants = listOf("word1", "word2", "word3")
            val indexOfCorrect = 0

            `when`(getTittleUseCase(word.definitions)).thenReturn(tittle)
            `when`(getOtherVariantsUseCase(word.name)).thenReturn(incorrectVariants)
            `when`(validateVariantsUseCase(incorrectVariants)).thenReturn(validatedIncorrectVariants)
            `when`(buildVariantsListUseCase(validatedIncorrectVariants, word.name)).thenReturn(
                allVariants
            )
            `when`(getRightVariantIndexUseCase(allVariants, word.name)).thenReturn(indexOfCorrect)

            val result = interactor.invoke(word)

            assertEquals(QuestionDto(tittle, allVariants.toImmutableList(), indexOfCorrect), result)
        }
}