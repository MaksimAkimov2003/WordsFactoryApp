package com.akimov.wordsfactory.screens.question.wrapper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akimov.domain.training.model.QuestionDto
import com.akimov.domain.training.model.TestDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionsWrapperViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val test: TestDto = savedStateHandle.get<TestDto>("") ?: throw Exception("Cannot find test")

    private val _currentQuestionNumber = MutableStateFlow(1)
    val currentQuestionNumber = _currentQuestionNumber.asStateFlow()

    private val _currentQuestion: MutableStateFlow<QuestionDto> = MutableStateFlow(test.questions.first())
    val currentQuestion = _currentQuestion.asStateFlow()

    val questionsCount = test.questionsCount

    var correctCount: Int = 0
        private set
    var incorrectCount: Int = 0
        private set


}