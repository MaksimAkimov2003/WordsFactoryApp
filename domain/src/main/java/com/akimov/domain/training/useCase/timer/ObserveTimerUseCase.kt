package com.akimov.domain.training.useCase.timer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

const val SECOND = 1000L
const val ITERATIONS_PER_SECOND = 10

class ObserveTimerUseCase {
    /**
     * @param durationInSeconds - сколько секунд будет идти таймер
     * @return обратный отсчёт времени
     */
    operator fun invoke(durationInSeconds: Int, withMillis: Boolean = false): Flow<Number> =
        if (withMillis) {
            withMillis(durationInSeconds)
        } else {
            withSeconds(durationInSeconds)
        }

    private fun withSeconds(durationInSeconds: Int): Flow<Int> =
        flow {
            repeat(durationInSeconds) {
                emit(durationInSeconds - it - 1)
                delay(SECOND)
            }
        }.flowOn(Dispatchers.Default)

    private fun withMillis(durationInSeconds: Int): Flow<Double> = flow {
        val totalIterations = durationInSeconds * ITERATIONS_PER_SECOND // 10 iterations per second
        repeat(totalIterations) {
            if (it == totalIterations - 1) {
                emit(0.0)
            } else {
                emit(
                    durationInSeconds.toDouble() -
                        it / ITERATIONS_PER_SECOND.toDouble()
                ) // Emit the remaining time in seconds
            }
            delay(SECOND / ITERATIONS_PER_SECOND) // Delay for 100 milliseconds (1/10 of a second)
        }
    }.flowOn(Dispatchers.Default)
}
