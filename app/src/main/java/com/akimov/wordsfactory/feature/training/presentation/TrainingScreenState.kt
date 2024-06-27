package com.akimov.wordsfactory.feature.training.presentation

import androidx.annotation.FloatRange

sealed class TrainingScreenState {
    data object NoWords : TrainingScreenState()

    data class EnoughWords(
        val wordsCount: Int,
        val trainingState: TrainingState,
    ) : TrainingScreenState()
}

sealed class TrainingState {
    data object NotStarted : TrainingState()

    data class Started(
        val secondsToStart: Int,
        // 4 - 80%, 3 - 60%, 2 - 40%, 1 - 20%, GO - 0%
        @FloatRange(from = 0.0, to = 1.0)
        val percentage: Float = (secondsToStart.toFloat() / TIMER_DURATION_SECONDS),
        val color: CircleColor =
            when (percentage) {
                0.8f -> CircleColor.Blue
                0.6f -> CircleColor.Green
                0.4f -> CircleColor.Yellow
                0.2f -> CircleColor.Red
                0.0f -> CircleColor.Orange
                else -> throw IllegalArgumentException()
            },
    ): TrainingState() {
        fun convertSecondsToText(): String {
            return when (secondsToStart) {
                0 -> "GO!"
                else -> secondsToStart.toString()
            }
        }
    }
}

enum class CircleColor {
    Blue,
    Green,
    Yellow,
    Red,
    Orange,
}
