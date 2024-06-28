package com.akimov.domain.statistics.useCase

import com.akimov.domain.statistics.repository.StatRepository

class SetUserStudiedTodayUseCase(
    private val statRepository: StatRepository,
) {
    operator fun invoke() {
        statRepository.setLastTrainingDate(System.currentTimeMillis())
    }
}