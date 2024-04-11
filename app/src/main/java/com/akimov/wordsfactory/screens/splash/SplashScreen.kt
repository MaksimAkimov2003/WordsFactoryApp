package com.akimov.wordsfactory.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

private const val START_DELAY = 500L

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(
    navigateToDictionary: () -> Unit,
    navigateToOnBoarding: () -> Unit,
) {
    val viewModel = koinViewModel<SplashViewModel>()
    val nextScreen by viewModel.state.collectAsState()

    val navigateForward: (() -> Unit)? by remember(nextScreen) {
        derivedStateOf {
            when (nextScreen) {
                NextScreen.UNKNOWN -> null
                NextScreen.MAIN -> navigateToDictionary
                NextScreen.ONBOARDING -> navigateToOnBoarding
            }
        }
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    var animationDurationMillis: Long? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = true) {
        delay(START_DELAY)
        isVisible = true
    }

    LaunchedEffect(key1 = animationDurationMillis) {
        animationDurationMillis?.let {
            delay(it)
            navigateForward?.let { navigate -> navigate() }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = isVisible,
            enter = slideInHorizontally()
        ) {
            animationDurationMillis = transition.totalDurationNanos / 1_000_000
            Image(
                painter = painterResource(id = R.drawable.splash),
                contentDescription = null
            )
        }
    }
}

@Preview(device = "id:pixel_6")
@Composable
private fun SplashScreenPreview() {
    WordsFactoryTheme {
        SplashScreen(
            navigateToDictionary = {},
            navigateToOnBoarding = {}
        )
    }
}
