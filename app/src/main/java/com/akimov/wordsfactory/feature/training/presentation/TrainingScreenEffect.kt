package com.akimov.wordsfactory.feature.training.presentation

import com.akimov.domain.training.model.WordTrainingDto

sealed class TrainingScreenEffect {
    data class NavigateNext(
        val wordsForTraining: List<WordTrainingDto>
    ) : TrainingScreenEffect()
}