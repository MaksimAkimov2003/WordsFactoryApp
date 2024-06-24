package com.akimov.wordsfactory.screens.question.wrapper.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.common.UiConstants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _effect = MutableSharedFlow<QuestionWrapperScreenEffect>()
    val effect = _effect.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    private var correctCount: Int = 0
    private var incorrectCount: Int = 0

    fun retrieveAnswer(isCorrect: Boolean) {
        if (isCorrect) {
            correctCount++
        } else {
            incorrectCount++
        }

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
        viewModelScope.launch {
            _effect.emit(
                QuestionWrapperScreenEffect.FinishTraining(
                    correctAnswers = correctCount,
                    incorrectAnswers = incorrectCount
                )
            )
        }
    }
}
