package com.akimov.wordsfactory.feature.question.wrapper.presentation

sealed class QuestionWrapperScreenEffect {
    data class FinishTraining(val correctAnswers: Int, val incorrectAnswers: Int) :
        QuestionWrapperScreenEffect()
}