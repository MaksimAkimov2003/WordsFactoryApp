package com.akimov.wordsfactory.feature.trainingFinished.presentation

import com.akimov.domain.training.model.WordTrainingDto

sealed class TrainingFinishedEffect {
    data object NavigateBack : TrainingFinishedEffect()
    data class StartTrainingAgain(val words: List<WordTrainingDto>) : TrainingFinishedEffect()
}