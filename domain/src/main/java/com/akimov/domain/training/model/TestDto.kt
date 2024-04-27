package com.akimov.domain.training.model

data class TestDto(
    val questions: List<QuestionDto>,
    val questionsCount: Int = questions.size
)
