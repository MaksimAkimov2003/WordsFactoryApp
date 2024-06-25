package com.akimov.wordsfactory.useCase.questions

import com.akimov.domain.training.useCase.words.GetTittleUseCase
import org.junit.Assert.assertTrue
import org.junit.Test

class GetTittleUseCaseTest {

    private val useCase = GetTittleUseCase()

    @Test
    fun `when definitions list is not empty, returned title should be from the list`() {
        val definitions = listOf("definition1", "definition2", "definition3")
        val title = useCase.invoke(definitions)
        assertTrue(definitions.contains(title))
    }

    @Test(expected = NoSuchElementException::class)
    fun `when definitions list is empty, should throw NoSuchElementException`() {
        val definitions = emptyList<String>()
        useCase.invoke(definitions)
    }
}