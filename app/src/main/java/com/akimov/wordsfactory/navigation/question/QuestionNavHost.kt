package com.akimov.wordsfactory.navigation.question

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.screens.question.QuestionScreen
import org.koin.androidx.compose.koinViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val PLACEHOLDER_START = "placeholder_start"
const val QUESTION_SCREEN = "question_screen"
const val WORD_ARG = "wordArg"
const val QUESTION_SCREEN_ROUTE = "$QUESTION_SCREEN/{$WORD_ARG}"

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
        startDestination = PLACEHOLDER_START
    ) {
        composable(route = PLACEHOLDER_START) {
            // Redirect to the real start destination with the argument
            val initialWordJson = Uri.encode(Json.encodeToString(getCurrentWord()))
            LaunchedEffect(Unit) {
                navController.navigate("$QUESTION_SCREEN/$initialWordJson") {
                    popUpTo(navController.graph.id)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        composable(
            route = QUESTION_SCREEN_ROUTE,
            arguments = listOf(navArgument(WORD_ARG) { type = NavType.StringType })
        ) {
            QuestionScreen(
                navigateNext = {
                    incrementCurrentWord()
                    val nextWordJson = Uri.encode(Json.encodeToString(getCurrentWord()))
                    navController.navigate("$QUESTION_SCREEN/$nextWordJson") {
                        popUpTo(navController.graph.id)
                    }
                },
            )
        }
    }
}

