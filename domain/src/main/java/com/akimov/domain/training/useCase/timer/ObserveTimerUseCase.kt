package com.akimov.domain.training.useCase.timer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val SECOND = 1000L

class ObserveTimerUseCase {
    /**
     * @param durationInSeconds - сколько секунд будет идти таймер
     * @return обратный отсчёт времени
     */
    operator fun invoke(durationInSeconds: Int) =
        flow {
            repeat(durationInSeconds) {
                emit(durationInSeconds - it - 1)
                delay(SECOND)
            }
        }.flowOn(Dispatchers.Default)
}