package com.akimov.wordsfactory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akimov.wordsfactory.screens.dictionary.DictionaryScreen
import com.akimov.wordsfactory.screens.login.LoginScreen
import com.akimov.wordsfactory.screens.onBoarding.OnBoardingScreen
import com.akimov.wordsfactory.screens.register.RegisterScreen
import com.akimov.wordsfactory.screens.splash.SplashScreen


private const val LAUNCH_SCREEN =  "LAUNCH_SCREEN"
private const val ON_BOARDING = "ON_BOARDING"
private const val SIGN_UP = "SIGN_UP"
private const val LOGIN = "LOGIN"
private const val DICTIONARY = "DICTIONARY"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LAUNCH_SCREEN
    ) {
        composable(route = LAUNCH_SCREEN) {
            SplashScreen(
                navigateToOnBoarding = {
                    navController.popBackStack()
                    navController.navigate(ON_BOARDING)
                },
                navigateToDictionary = {
                    navController.popBackStack()
                    navController.navigate(DICTIONARY)
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
                navController.navigate(DICTIONARY) {
                    popUpTo(navController.graph.id)
                }
            }
        }

        composable(route = LOGIN) {
            LoginScreen(
                navigateUp = {
                    navController.navigate(DICTIONARY) {
                        popUpTo(navController.graph.id)
                    }
                },
                navigateToSignUp = {
                    navController.navigate(SIGN_UP)
                }
            )
        }

        composable(route = DICTIONARY) {
            DictionaryScreen()
        }
    }
}