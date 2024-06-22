package com.akimov.wordsfactory.navigation.common

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.common.UiConstants
import com.akimov.wordsfactory.navigation.bottomNav.BottomNavHost
import com.akimov.wordsfactory.screens.login.LoginScreen
import com.akimov.wordsfactory.screens.onBoarding.OnBoardingScreen
import com.akimov.wordsfactory.screens.question.wrapper.QuestionsWrapperScreen
import com.akimov.wordsfactory.screens.register.RegisterScreen
import com.akimov.wordsfactory.screens.splash.SplashScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val LAUNCH_SCREEN = "LAUNCH_SCREEN"
private const val ON_BOARDING = "ON_BOARDING"
private const val SIGN_UP = "SIGN_UP"
private const val LOGIN = "LOGIN"

private const val BOTTOM_NAV_HOST = "BOTTOM_NAV_HOST"

private const val QUESTIONS_HOST = "QUESTIONS_HOST"

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
                }
            )
        }

        // Questions host
        composable(
            route = "$QUESTIONS_HOST/{${UiConstants.JSON_WORDS_IN_TEST}}",
            arguments = listOf(navArgument(UiConstants.JSON_WORDS_IN_TEST) { type = NavType.StringType })
        ) {
            QuestionsWrapperScreen()
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
    }
}

