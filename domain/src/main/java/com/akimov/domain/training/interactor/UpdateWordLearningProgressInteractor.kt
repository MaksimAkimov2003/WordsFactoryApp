package com.akimov.domain.training.interactor

import com.akimov.domain.training.useCase.words.CalculateWordCoefficientUseCase
import com.akimov.domain.training.useCase.words.GetWordByIdUseCase
import com.akimov.domain.training.useCase.words.UpdateWordCoefficientUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @param coroutineScope - для того, чтобы обновить коэфициент в фоне,
 * не зависимо от скоупа UI.
 */
class UpdateWordLearningProgressInteractor(
    private val coroutineScope: CoroutineScope,
    private val getWordByIdUseCase: GetWordByIdUseCase,
    private val calculateWordCoefficientUseCase: CalculateWordCoefficientUseCase,
    private val updateWordCoefficientUseCase: UpdateWordCoefficientUseCase
) {
    operator fun invoke(
        wordId: String,
        isAnswerCorrect: Boolean
    ) = coroutineScope.launch {
        val word = getWordByIdUseCase(wordId = wordId)

        val newCoefficient = calculateWordCoefficientUseCase(
            oldWordCoefficient = word.knowledgeCoefficient,
            isAnswerCorrect = isAnswerCorrect
        )

        updateWordCoefficientUseCase(
            word = word,
            newCoefficient = newCoefficient
        )
    }
}