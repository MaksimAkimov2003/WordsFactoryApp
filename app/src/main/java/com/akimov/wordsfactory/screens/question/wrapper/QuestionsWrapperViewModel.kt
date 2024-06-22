package com.akimov.wordsfactory.screens.question.wrapper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.common.UiConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionsWrapperViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val jsonWordsInTest: String = savedStateHandle[UiConstants.JSON_WORDS_IN_TEST]
        ?: error("Words are not provided")
    private val wordsInTest: List<WordTrainingDto> = Json.decodeFromString(jsonWordsInTest)

    private val _currentQuestionNumber = MutableStateFlow(1)

    val currentQuestionNumber = _currentQuestionNumber.asStateFlow()
    private val _currentWord = MutableStateFlow(wordsInTest.first())

    val currentWord = _currentWord.asStateFlow()
    val questionsCount = wordsInTest.count()

    var correctCount: Int = 0
        private set

    var incorrectCount: Int = 0
        private set

    fun incrementCurrentWord() {
        val currentWordIndex = wordsInTest.indexOf(_currentWord.value)

        if (currentWordIndex < wordsInTest.size - 1) {
            _currentWord.update {
                wordsInTest[currentWordIndex + 1]
            }
            _currentQuestionNumber.update {
                it + 1
            }
        } else {
            finishTest()
        }
    }

    private fun finishTest() {
        TODO("Not yet implemented")
    }
}
