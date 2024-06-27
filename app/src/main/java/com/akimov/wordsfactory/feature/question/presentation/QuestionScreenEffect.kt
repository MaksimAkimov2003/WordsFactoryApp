package com.akimov.wordsfactory.feature.question.presentation

sealed class QuestionScreenEffect {
    data class NavigateNext(
        val isAnswerCorrect: Boolean
    ) : QuestionScreenEffect()
}