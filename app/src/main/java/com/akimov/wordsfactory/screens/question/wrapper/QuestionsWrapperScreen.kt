package com.akimov.wordsfactory.screens.question.wrapper

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.akimov.domain.training.model.QuestionDto
import com.akimov.wordsfactory.common.theme.paragraphLarge
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionsWrapperScreen(
) {
    val viewModel: QuestionsWrapperViewModel = koinViewModel()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val currentQuestionNumber by viewModel.currentQuestionNumber.collectAsState()

    QuestionsWrapperScreenStateless(
        getQuestion = { currentQuestion },
        getQuestionNumber = { currentQuestionNumber },
        getQuestionCount = { viewModel.questionsCount }
    )
}

@Composable
private fun QuestionsWrapperScreenStateless(
    getQuestion: () -> QuestionDto,
    getQuestionNumber: () -> Int,
    getQuestionCount: () -> Int,
) {
    Scaffold(
        topBar = {
            Text(
                text = "${getQuestionNumber()} of ${getQuestionCount()}",
                style = MaterialTheme.typography.paragraphLarge
            )
        }
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(paddingValues.calculateTopPadding()))
        // Question Screen

    }
}