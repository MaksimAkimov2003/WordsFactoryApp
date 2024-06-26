package com.akimov.wordsfactory.navigation.bottomNav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.common.components.bottomNavigation.AppBottomBar
import com.akimov.wordsfactory.screens.dictionary.DictionaryScreen
import com.akimov.wordsfactory.screens.training.TrainingScreen
import com.akimov.wordsfactory.screens.video.VideoScreen

// Bottom nav
const val DICTIONARY = "DICTIONARY"
const val TRAINING = "TRAINING"
const val VIDEO = "VIDEO"

@Composable
fun BottomNavHost(
    navigateToQuestion: (List<WordTrainingDto>) -> Unit,
    navigateToPlayer: () -> Unit
) {
    val bottomHostNavController = rememberNavController()

    val navBackStackEntry by bottomHostNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            AppBottomBar(
                selectedItem = when (currentRoute) {
                    DICTIONARY -> NavBarItem.DICTIONARY
                    TRAINING -> NavBarItem.TRAINING
                    VIDEO -> NavBarItem.VIDEO
                    else -> NavBarItem.DICTIONARY
                },
                changeSelectedItem = { navItem ->
                    bottomHostNavController.navigate(navItem.route) {
                        if (navItem == NavBarItem.DICTIONARY) {
                            popUpTo(bottomHostNavController.graph.id) {
                                saveState = true
                                inclusive = true
                            }
                        } else {
                            popUpTo(navItem.route) {
                                saveState = true
                                inclusive = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = bottomHostNavController,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            startDestination = DICTIONARY
        ) {
            composable(route = DICTIONARY) {
                DictionaryScreen()
            }

            composable(route = TRAINING) {
                TrainingScreen(
                    navigateNext = navigateToQuestion,
                )
            }

            composable(route = VIDEO) {
                VideoScreen(
                    navigateBack = {
                        bottomHostNavController.popBackStack()
                    },
                    navigateToPlayer = navigateToPlayer
                )
            }
        }
    }
}