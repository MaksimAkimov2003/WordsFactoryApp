package com.akimov.wordsfactory.screens.question.wrapper

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.common.theme.paragraphLarge
import com.akimov.wordsfactory.navigation.question.QuestionsNavHost
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionsWrapperScreen() {
    val viewModel: QuestionsWrapperViewModel = koinViewModel()
    val currentWord by viewModel.currentWord.collectAsState()
    val currentQuestionNumber by viewModel.currentQuestionNumber.collectAsState()

    QuestionsWrapperScreenStateless(
        getWord = { currentWord },
        getQuestionNumber = { currentQuestionNumber },
        getQuestionCount = { viewModel.questionsCount },
        incrementCurrentWord = { viewModel.incrementCurrentWord() }
    )
}

@Composable
private fun QuestionsWrapperScreenStateless(
    getWord: () -> WordTrainingDto,
    getQuestionNumber: () -> Int,
    getQuestionCount: () -> Int,
    incrementCurrentWord: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "${getQuestionNumber()} of ${getQuestionCount()}",
                    style = MaterialTheme.typography.paragraphLarge
                )
            }
        }
    ) { paddingValues ->
        // Question Screen
        QuestionsNavHost(
            modifier = Modifier.padding(top = 8.dp + paddingValues.calculateTopPadding()),
            getCurrentWord = getWord,
            incrementCurrentWord = incrementCurrentWord
        )
    }
}

@Preview
@Composable
private fun QuestionsWrapperScreenStatelessPreview() {
    QuestionsWrapperScreenStateless(
        getWord = {
            WordTrainingDto(
                id = "1",
                name = "Word",
                definitions = listOf("Definition 1", "Definition 2")
            )
        },
        getQuestionNumber = { 1 },
        getQuestionCount = { 10 },
        incrementCurrentWord = {}
    )
}
