package com.akimov.wordsfactory.navigation.question

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.screens.question.QuestionScreen
import com.akimov.wordsfactory.screens.question.QuestionViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

private const val QUESTION_SCREEN = "QUESTION_SCREEN"

@Composable
fun QuestionsNavHost(
    getCurrentWord: () -> WordTrainingDto,
    incrementCurrentWord: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = QUESTION_SCREEN
    ) {
        composable(route = QUESTION_SCREEN) {
            QuestionScreen(
                viewModel = createQuestionViewModel(currentWord = getCurrentWord()),
                navigateNext = {
                    incrementCurrentWord()
                    navController.navigate(QUESTION_SCREEN) {
                        popUpTo(navController.graph.id)
                    }
                }
            )
        }
    }
}

@Composable
fun createQuestionViewModel(currentWord: WordTrainingDto): QuestionViewModel {
    return QuestionViewModel(
        getQuestionForWordInteractor = koinInject(),
        observeTimerUseCase = koinInject(),
        word = currentWord
    )
}
