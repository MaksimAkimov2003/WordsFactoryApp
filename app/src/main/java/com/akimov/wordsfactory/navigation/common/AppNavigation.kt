package com.akimov.wordsfactory.navigation.common

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akimov.wordsfactory.common.UiConstants
import com.akimov.wordsfactory.feature.login.LoginScreen
import com.akimov.wordsfactory.feature.onBoarding.OnBoardingScreen
import com.akimov.wordsfactory.feature.question.wrapper.QuestionsWrapperScreen
import com.akimov.wordsfactory.feature.register.RegisterScreen
import com.akimov.wordsfactory.feature.splash.SplashScreen
import com.akimov.wordsfactory.feature.trainingFinished.TrainingFinishedScreen
import com.akimov.wordsfactory.feature.video.VideoViewHolder
import com.akimov.wordsfactory.feature.video.player.VideoPlayerScreen
import com.akimov.wordsfactory.navigation.bottomNav.BottomNavHost
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val LAUNCH_SCREEN = "LAUNCH_SCREEN"
private const val ON_BOARDING = "ON_BOARDING"
private const val SIGN_UP = "SIGN_UP"
private const val LOGIN = "LOGIN"

private const val BOTTOM_NAV_HOST = "BOTTOM_NAV_HOST"

private const val QUESTIONS_HOST = "QUESTIONS_HOST"

private const val TRAINING_FINISHED = "TRAINING_FINISHED"

private const val VIDEO_PLAYER = "VIDEO_PLAYER"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LAUNCH_SCREEN
    ) {
        // Bottom navigation host
        composable(route = BOTTOM_NAV_HOST) {
            BottomNavHost(
                navigateToQuestion = { wordsForTest ->
                    val jsonWordsForTest = Uri.encode(Json.encodeToString(wordsForTest))
                    navController.navigate("$QUESTIONS_HOST/$jsonWordsForTest") {
                        popUpTo(navController.graph.id)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToPlayer = {
                    navController.navigate(route = VIDEO_PLAYER) {
//                        popUpTo(route = BOTTOM_NAV_HOST) {
//                            saveState = true
//                        }
//
//                        launchSingleTop = true
//                        restoreState = true
                    }
                }
            )
        }

        // Questions host
        composable(
            route = "$QUESTIONS_HOST/{${UiConstants.JSON_WORDS_IN_TEST}}",
            arguments = listOf(
                navArgument(UiConstants.JSON_WORDS_IN_TEST) {
                    type = NavType.StringType
                }
            )
        ) {
            QuestionsWrapperScreen(
                onTestFinished = { correct: Int, incorrect: Int ->
                    navController.navigate("$TRAINING_FINISHED/$correct/$incorrect") {
                        popUpTo(navController.graph.id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = LAUNCH_SCREEN) {
            SplashScreen(
                navigateToOnBoarding = {
                    navController.navigate(ON_BOARDING) {
                        popUpTo(navController.graph.id)
                    }
                },
                navigateToDictionary = {
                    navController.navigate(BOTTOM_NAV_HOST) {
                        popUpTo(navController.graph.id)
                    }
                }
            )
        }

        composable(route = ON_BOARDING) {
            OnBoardingScreen {
                navController.navigate(LOGIN)
            }
        }

        composable(route = SIGN_UP) {
            RegisterScreen {
                navController.navigate(BOTTOM_NAV_HOST) {
                    popUpTo(navController.graph.id)
                }
            }
        }

        composable(route = LOGIN) {
            LoginScreen(
                navigateUp = {
                    navController.navigate(BOTTOM_NAV_HOST) {
                        popUpTo(navController.graph.id)
                    }
                },
                navigateToSignUp = {
                    navController.navigate(SIGN_UP)
                }
            )
        }

        composable(
            route = "$TRAINING_FINISHED/{${UiConstants.CORRECT}}/{${UiConstants.INCORRECT}}",
            arguments = listOf(
                navArgument(UiConstants.CORRECT) { type = NavType.IntType },
                navArgument(UiConstants.INCORRECT) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val (correct, incorrect) = with(backStackEntry.arguments) {
                (
                        this?.getInt(UiConstants.CORRECT)
                            ?: 0
                        ) to (this?.getInt(UiConstants.INCORRECT) ?: 0)
            }

            TrainingFinishedScreen(
                correct = correct,
                incorrect = incorrect,
                navigateToQuestions = { wordsForTest ->
                    val jsonWordsForTest = Uri.encode(Json.encodeToString(wordsForTest))
                    navController.navigate("$QUESTIONS_HOST/$jsonWordsForTest") {
                        popUpTo(navController.graph.id)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateBack = {
                    navController.navigate(BOTTOM_NAV_HOST) {
                        popUpTo(navController.graph.id)
                    }
                }
            )
        }

        composable(route = VIDEO_PLAYER) {
            VideoPlayerScreen(
                videoView = VideoViewHolder.getVideoView(),
            )
        }
    }
}
