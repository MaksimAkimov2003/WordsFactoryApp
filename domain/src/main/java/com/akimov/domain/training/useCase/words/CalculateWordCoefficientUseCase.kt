package com.akimov.domain.training.useCase.words

class CalculateWordCoefficientUseCase {
    operator fun invoke(oldWordCoefficient: Int, isAnswerCorrect: Boolean): Int =
        if (isAnswerCorrect) {
            oldWordCoefficient + 1
        } else {
            oldWordCoefficient - 1
        }
}
