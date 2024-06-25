package com.akimov.wordsfactory.useCase.words

import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.domain.training.useCase.words.GetWordsForTrainingUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetWordsForTrainingUseCaseTest {

    private lateinit var wordsRepository: WordsRepository
    private lateinit var useCase: GetWordsForTrainingUseCase

    @Before
    fun setup() {
        wordsRepository = mock(WordsRepository::class.java)
        useCase = GetWordsForTrainingUseCase(wordsRepository)
    }

    @Test
    fun `when repository returns words, use case should return the same words`() = runBlocking {
        val words = listOf(
            WordTrainingDto(
                id = "1",
                name = "word1",
                definitions = listOf("definition1", "definition2"),
            ),
            WordTrainingDto(
                id = "2",
                name = "word2",
                definitions = listOf("definition3", "definition4"),
            ),
            WordTrainingDto(
                id = "3",
                name = "word3",
                definitions = listOf("definition5", "definition6"),
            ),
        )
        `when`(wordsRepository.getSortedByCoefficientWords(GetWordsForTrainingUseCase.MAX_WORDS_COUNT)).thenReturn(
            words
        )

        val result = useCase.invoke()

        assertEquals(words, result)
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        `when`(wordsRepository.getSortedByCoefficientWords(GetWordsForTrainingUseCase.MAX_WORDS_COUNT)).thenReturn(
            emptyList()
        )

        val result = useCase.invoke()

        assertEquals(emptyList<String>(), result)
    }
}