package com.akimov.wordsfactory.screens.training.presentation

import com.akimov.domain.training.model.WordTrainingDto

sealed class TrainingScreenEffect {
    data class NavigateNext(
        val wordsForTraining: List<WordTrainingDto>
    ) : TrainingScreenEffect()
}