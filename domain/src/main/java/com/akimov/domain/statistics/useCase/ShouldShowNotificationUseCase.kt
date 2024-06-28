package com.akimov.domain.statistics.useCase

import com.akimov.domain.statistics.repository.StatRepository

class ShouldShowNotificationUseCase(
    private val statRepository: StatRepository
) {
    operator fun invoke(): Boolean {
        val lastTrainingDate = statRepository.getLastTrainingDate()

        if (lastTrainingDate == -1L) return true

        val currentTime = System.currentTimeMillis()
        val lastTrainingDatePlusDay = lastTrainingDate + 24 * 60 * 60 * 1000
        return currentTime >= lastTrainingDatePlusDay
    }
}