package com.akimov.domain.training.useCase.words

class GetTittleUseCase {
    operator fun invoke(definitions: List<String>) = definitions.random()
}