package com.akimov.wordsfactory.navigation.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akimov.wordsfactory.navigation.bottomNav.BottomNavHost
import com.akimov.wordsfactory.screens.login.LoginScreen
import com.akimov.wordsfactory.screens.onBoarding.OnBoardingScreen
import com.akimov.wordsfactory.screens.register.RegisterScreen
import com.akimov.wordsfactory.screens.splash.SplashScreen


private const val LAUNCH_SCREEN = "LAUNCH_SCREEN"
private const val ON_BOARDING = "ON_BOARDING"
private const val SIGN_UP = "SIGN_UP"
private const val LOGIN = "LOGIN"

private const val BOTTOM_NAV_HOST = "BOTTOM_NAV_HOST"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LAUNCH_SCREEN
    ) {
        // Bottom navigation host
        composable(route = BOTTOM_NAV_HOST) {
            BottomNavHost()
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
