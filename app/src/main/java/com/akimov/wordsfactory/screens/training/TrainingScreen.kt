package com.akimov.wordsfactory.screens.training

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TrainingScreen(
    navigateToTraining: () -> Unit,
    text: String,
) {
    Text(text, Modifier.clickable {
        navigateToTraining()
    })
}