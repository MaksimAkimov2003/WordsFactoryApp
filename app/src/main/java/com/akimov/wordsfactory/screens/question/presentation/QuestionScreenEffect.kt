package com.akimov.wordsfactory.screens.question.presentation

sealed class QuestionScreenEffect {
    data class NavigateNext(
        val isAnswerCorrect: Boolean
    ) : QuestionScreenEffect()
}